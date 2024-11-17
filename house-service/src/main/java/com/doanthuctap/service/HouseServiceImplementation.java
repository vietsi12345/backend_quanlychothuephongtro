package com.doanthuctap.service;

import com.doanthuctap.model.CommunesDto;
import com.doanthuctap.model.House;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.HouseWithMinPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HouseServiceImplementation implements HouseService{

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CommunesService communesService;

    @Override
    public HouseWithMinPrice createHouse(House house, MultipartFile imageFile) throws Exception {
        if (imageFile != null && !imageFile.isEmpty()) {
            house.setImage(imageFile.getBytes());
        }
        house.setIsActive(true);
        houseRepository.save(house);

        // trả về để xuất ra fontend
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
        return houseWithMinPrice ;
    }

    @Override
    public HouseWithMinPrice getHouseById(Long id) throws Exception {
        House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not founds"));
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

        return houseWithMinPrice;
    }

    @Override
    public List<HouseWithMinPrice> getAllHouse() throws Exception {
        List<House> houses = houseRepository.findAll();
        List <HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();
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
        return houseWithMinPrices;
    }

    @Override
    public HouseWithMinPrice updateHouse(Long id, String name, String description, String idCommune, String addressDetials, MultipartFile imageFile) throws Exception {
        House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not founds"));

        if (name != null && !name.isEmpty()) {
            house.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            house.setDescription(description);
        }
        if (idCommune != null && !idCommune.isEmpty()) {
            house.setIdCommune(Long.valueOf(idCommune));
        }
        if (addressDetials != null && !addressDetials.isEmpty()) {
            house.setAddressDetails(addressDetials);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            house.setImage(imageFile.getBytes());
        }
        houseRepository.save(house);
        // đổi để xuất ra frontend
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
        return houseWithMinPrice;
    }
    @Override
    public HouseWithMinPrice deleteHouse(Long id ) throws Exception {
      House house = houseRepository.findById(id).orElseThrow(() -> new Exception("House not founds"));
      house.setIsActive(false);
      houseRepository.save(house);

      // đổi để xuất ra fontend
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
        return houseWithMinPrice;
    }

    @Override
    public List<String> getAllDistinctCities() throws Exception {
        // Lấy danh sách idCommune từ HouseRepository
        List<Long> allDistinctCommuneIds = houseRepository.findDistinctActiveCommuneIds();

        // Khai báo Set để chứa tên tỉnh/thành phố duy nhất
        Set<String> uniqueProvinceNames = new HashSet<>();

        // Lấy danh sách CommunesDto và tên tỉnh/thành phố
        for (Long communeId : allDistinctCommuneIds) {
            CommunesDto commune = communesService.getCommunesById(communeId);
            String provinceName = commune.getDistrict().getProvince().getName();
            uniqueProvinceNames.add(provinceName); // Chỉ thêm tên tỉnh/thành phố duy nhất
        }

        // Chuyển Set thành List trước khi trả về
        return new ArrayList<>(uniqueProvinceNames);
    }

    @Override
    public List<HouseWithMinPrice> getHousesByCity(String city) throws Exception {
        List <House> houses = houseRepository.findAll();
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();

        for (House house : houses) {
            Long idCommune= house.getIdCommune();
            CommunesDto commune = communesService.getCommunesById(idCommune);
            String provinceName = commune.getDistrict().getProvince().getName();
            System.out.println(provinceName);
            if (city.equals(provinceName)) {
                BigDecimal minRoomPrice = roomRepository.findMinPriceByHouseId(house.getId());
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
        }
        return houseWithMinPrices;
    }

    @Override
    public List<HouseWithMinPrice> getSearchByName(String name) throws Exception {
        List <House> houses = houseRepository.findByNameContaining(name);
        List<HouseWithMinPrice> houseWithMinPrices = new ArrayList<>();

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
        return houseWithMinPrices;
    }

    @Override
    public List<House> getAllHouseIsActive() {
        return houseRepository.findByIsActiveTrue();
    }

}
