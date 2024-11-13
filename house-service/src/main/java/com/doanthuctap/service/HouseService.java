package com.doanthuctap.service;

import com.doanthuctap.model.CommunesDto;
import com.doanthuctap.model.House;
import com.doanthuctap.response.HouseWithMinPrice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HouseService {
    HouseWithMinPrice createHouse(House house, MultipartFile imageFile) throws Exception;

    HouseWithMinPrice getHouseById (Long id) throws  Exception;

    List <HouseWithMinPrice> getAllHouse () throws  Exception;

    HouseWithMinPrice updateHouse(Long id, String name, String description, String idCommune, String addressDetials, MultipartFile imageFile) throws Exception;

    HouseWithMinPrice deleteHouse (Long id) throws  Exception;

    List<String> getAllDistinctCities () throws  Exception;

    List<HouseWithMinPrice> getHousesByCity (String city) throws  Exception;
//
    List <HouseWithMinPrice> getSearchByName (String name) throws Exception;

    List <House> getAllHouseIsActive ();
}
