package com.doanthuctap.service;

import com.doanthuctap.model.Address;
import com.doanthuctap.model.House;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.HouseWithMinPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class HouseServiceImplementation implements HouseService{

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public House createHouse(House house, MultipartFile imageFile) throws Exception {
        if (imageFile != null && !imageFile.isEmpty()) {
            house.setImage(imageFile.getBytes());
        }
        return houseRepository.save(house);
    }

    @Override
    public HouseWithMinPrice getHouseById(Long id) throws Exception {
        House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not found"));
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

        return houseWithMinPrice;
    }

    @Override
    public List<House> getAllHouse() throws Exception {
        List<House> houses = houseRepository.findAll();
        return houses;
    }

    @Override
    public House updateHouse(Long id, String name, String description, String street, String ward, String district, String city, MultipartFile imageFile) throws Exception {
        House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not found"));

        if (name != null && !name.isEmpty()) {
            house.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            house.setDescription(description);
        }
        if ((street != null && !street.isEmpty()) ||
                (ward != null && !ward.isEmpty()) ||
                (district != null && !district.isEmpty()) ||
                (city != null && !city.isEmpty())) {

            Address address = house.getAddress();
            if (address == null) {
                address = new Address();
            }
            if (street != null && !street.isEmpty()) {
                address.setStreet(street);
            }
            if (ward != null && !ward.isEmpty()) {
                address.setWard(ward);
            }
            if (district != null && !district.isEmpty()) {
                address.setDistrict(district);
            }
            if (city != null && !city.isEmpty()) {
                address.setCity(city);
            }
            house.setAddress(address);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            house.setImage(imageFile.getBytes());
        }

        return houseRepository.save(house);
    }
    @Override
    public void deleteHouse(Long id ) throws Exception {
      House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not found"));
      houseRepository.delete(house);
    }

    @Override
    public List<String> getAllDistinctCities() throws Exception {
        return houseRepository.findAllDistinctCities();
    }

    @Override
    public List<HouseWithMinPrice> getHousesByCity(String city) throws Exception {
        List <House> houses = houseRepository.findByAddress_City(city);
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();

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
        return houseWithMinPrices;
    }

    @Override
    public List<HouseWithMinPrice> getSearchByName(String name) throws Exception {
        List <House> houses = houseRepository.findByNameContaining(name);
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();

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
        return houseWithMinPrices;
    }
}
