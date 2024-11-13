package com.doanthuctap.controller;

import com.doanthuctap.model.CommunesDto;
import com.doanthuctap.model.House;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.HouseWithMinPrice;
import com.doanthuctap.service.CommunesService;
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

    @Autowired
    private CommunesService communesService;

    @PostMapping
    public ResponseEntity<HouseWithMinPrice> createHouse(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("idCommune") Long idCommune,
            @RequestParam("addressDetails") String addressDetails,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        House house = new House();
        house.setName(name);
        house.setDescription(description);
        house.setAddressDetails(addressDetails);
        house.setIdCommune(idCommune);
        HouseWithMinPrice createdHouse = houseService.createHouse(house, imageFile);
        return ResponseEntity.ok(createdHouse);
    }
    @GetMapping
    public ResponseEntity<List<HouseWithMinPrice>> getAllHouse () throws Exception {
        List<HouseWithMinPrice> allHouse = houseService.getAllHouse();
        return ResponseEntity.ok(allHouse);
    }

    @GetMapping("/{id}")
    public ResponseEntity <HouseWithMinPrice> getHouseById(@PathVariable Long id) throws Exception {
        HouseWithMinPrice  house = houseService.getHouseById(id);
        return  ResponseEntity.ok(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseWithMinPrice> updateHouse(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "idCommune", required = false) String idCommune,
            @RequestParam(value = "addressDetails", required = false) String addressDetails,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        HouseWithMinPrice updatedHouse = houseService.updateHouse(id, name, description, idCommune, addressDetails, imageFile);
        return ResponseEntity.ok(updatedHouse);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<HouseWithMinPrice> deleteHouse (@PathVariable Long id) throws Exception {
        HouseWithMinPrice houseCancel = houseService.deleteHouse(id);
        return ResponseEntity.ok (houseCancel);
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


    @GetMapping("/search")
    public ResponseEntity<List<HouseWithMinPrice>> searchHousesByName( @RequestParam(value = "name", required = false) String name) throws Exception {
        List <House> houses = new ArrayList<>();
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            houses = houseService.getAllHouseIsActive();
            for (House house : houses) {
                BigDecimal minRoomPrice = roomRepository.findMinPriceByHouseId(house.getId());
                CommunesDto commune = communesService.getCommunesById(house.getIdCommune());

                HouseWithMinPrice houseWithMinPrice = new HouseWithMinPrice();
                houseWithMinPrice.setId(house.getId());
                houseWithMinPrice.setName(house.getName());
                houseWithMinPrice.setDescription(house.getDescription());
                houseWithMinPrice.setAddressDetails(house.getAddressDetails());
                houseWithMinPrice.setIsActive(house.getIsActive());
                houseWithMinPrice.setIdCommune(house.getIdCommune());
                houseWithMinPrice.setCommune(commune.getName());
                houseWithMinPrice.setDistrict(commune.getDistrict().getName());
                houseWithMinPrice.setProvince(commune.getDistrict().getProvince().getName());
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
