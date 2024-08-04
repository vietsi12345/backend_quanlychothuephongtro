package com.doanthuctap.controller;

import com.doanthuctap.model.Address;
import com.doanthuctap.model.House;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.HouseWithMinPrice;
import com.doanthuctap.response.RoomTypeDto;
import com.doanthuctap.service.HouseService;
import com.doanthuctap.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<House> createHouse(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("street") String street,
            @RequestParam("ward") String ward,
            @RequestParam("district") String district,
            @RequestParam("city") String city,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        House house = new House();
        house.setName(name);
        house.setDescription(description);

        Address address = new Address();
        address.setStreet(street);
        address.setWard(ward);
        address.setDistrict(district);
        address.setCity(city);
        house.setAddress(address);

        House createdHouse = houseService.createHouse(house, imageFile);
        return ResponseEntity.ok(createdHouse);
    }
    @GetMapping
    public ResponseEntity<List<House>> getAllHouse () throws Exception {
        List<House> allHouse = houseService.getAllHouse();
        return ResponseEntity.ok(allHouse);
    }

    @GetMapping("/{id}")
    public ResponseEntity <HouseWithMinPrice> getHouseById(@PathVariable Long id) throws Exception {
        HouseWithMinPrice  house = houseService.getHouseById(id);
        return  ResponseEntity.ok(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> updateHouse(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "ward", required = false) String ward,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        House updatedHouse = houseService.updateHouse(id, name, description, street, ward, district, city, imageFile);
        return ResponseEntity.ok(updatedHouse);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteHouse (@PathVariable Long id) throws Exception {
        houseService.deleteHouse(id);
        return ResponseEntity.ok ("Xóa thành công nhà có id "+id);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() throws Exception {
        List<String> cities = houseService.getAllDistinctCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/houses-by-city")
    public ResponseEntity<List<HouseWithMinPrice>> getHousesByCity(@RequestParam String city) throws Exception {
        List<HouseWithMinPrice> houses = houseService.getHousesByCity(city);
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/{houseId}/rooms-by-type")
    public ResponseEntity<List<RoomTypeDto>> getRoomsByType(@PathVariable Long houseId) {
        List<RoomTypeDto> roomTypes = roomService.getRoomTypesWithRoomsByHouseId(houseId);
        return ResponseEntity.ok(roomTypes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HouseWithMinPrice>> searchHousesByName( @RequestParam(value = "name", required = false) String name) throws Exception {
        List <House> houses = new ArrayList<>();
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            houses = houseService.getAllHouse();
            for (House house : houses) {
                BigDecimal minRoomPrice = roomRepository.findMinPriceByHouseId(house.getId());

                HouseWithMinPrice houseWithMinPrice = new HouseWithMinPrice();
                houseWithMinPrice.setId(house.getId());
                houseWithMinPrice.setName(house.getName());
                houseWithMinPrice.setDescription(house.getDescription());
                houseWithMinPrice.setStreet(house.getAddress().getStreet());
                houseWithMinPrice.setWard(house.getAddress().getWard());
                houseWithMinPrice.setDistrict(house.getAddress().getDistrict());
                houseWithMinPrice.setCity(house.getAddress().getCity());
                houseWithMinPrice.setMinRoomPrice(minRoomPrice != null ? minRoomPrice : BigDecimal.ZERO);

                if (house.getImage() != null) {
                    houseWithMinPrice.setImageBase64(Base64.getEncoder().encodeToString(house.getImage()));
                }

                houseWithMinPrices.add(houseWithMinPrice);
            }
        } else {
            houseWithMinPrices = houseService.getSearchByName(name);
        }
        return ResponseEntity.ok(houseWithMinPrices);
    }
}
