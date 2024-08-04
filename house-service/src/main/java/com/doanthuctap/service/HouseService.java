package com.doanthuctap.service;

import com.doanthuctap.model.House;
import com.doanthuctap.response.HouseWithMinPrice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HouseService {
    House createHouse(House house, MultipartFile imageFile) throws Exception;

    HouseWithMinPrice getHouseById (Long id) throws  Exception;

    List <House> getAllHouse () throws  Exception;

    House updateHouse (Long id, String name, String description, String street, String ward, String district, String city, MultipartFile imageFile)  throws  Exception;

    void deleteHouse (Long id) throws  Exception;

    List<String> getAllDistinctCities () throws  Exception;

    List<HouseWithMinPrice> getHousesByCity (String city) throws  Exception;

    List <HouseWithMinPrice> getSearchByName (String name) throws Exception;
}
