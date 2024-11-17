package com.doanthuctap.service;

import com.doanthuctap.model.*;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.RoomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomFilterService {

    @Autowired
    private CommunesService communesService;
    @Autowired
    private RoomRepository roomRepository;
    public List<RoomFilter> filterRooms(
            Long provinceId,
            Long districtId,
            Long communeId,
            String priceRange,
            String areaRange) throws Exception {
        List <Room> rooms = roomRepository.findByStatusNot(RoomStatus.DELETED);
        List<RoomFilter> roomFilters = new ArrayList<>();
        for (Room room:rooms){
            RoomFilter roomFilter = converRoomtoRoomFilter(room);
            roomFilters.add(roomFilter);
        }
        return roomFilters.stream()
                .filter(roomFilter -> filterByLocation(roomFilter, provinceId, districtId, communeId))
                .filter(roomFilter -> filterByPrice(roomFilter, priceRange))
                .filter(roomFilter -> filterByArea(roomFilter, areaRange))
                .collect(Collectors.toList());
    }

    private boolean filterByLocation(
            RoomFilter room,
            Long provinceId,
            Long districtId,
            Long communeId) {
        // Nếu không chọn tỉnh/thành phố nào, trả về tất cả
        if (provinceId == null) {
            return true;
        }
        // Kiểm tra tỉnh/thành phố
        if (!room.getCommune().getDistrict().getProvince().getId().equals(provinceId)) {
            return false;
        }
        // Kiểm tra quận/huyện nếu được chọn
        if (districtId != null) {
            if (!room.getCommune().getDistrict().getId().equals(districtId)) {
                return false;
            }
        }
        // Kiểm tra phường/xã nếu được chọn
        if (communeId != null) {
            if (!room.getCommune().getId().equals(communeId)) {
                return false;
            }
        }
        return true;
    }

    private boolean filterByPrice(RoomFilter room, String priceRange) {
        if (priceRange == null || priceRange.equals("all")) {
            return true;
        }

        BigDecimal price = room.getPrice();
        BigDecimal million = new BigDecimal("1000000");

        return switch (priceRange) {
            case "under1" -> price.compareTo(million) < 0;
            case "1-2" -> price.compareTo(million) >= 0 && price.compareTo(million.multiply(new BigDecimal("2"))) <= 0;
            case "2-3" -> price.compareTo(million.multiply(new BigDecimal("2"))) > 0 &&
                    price.compareTo(million.multiply(new BigDecimal("3"))) <= 0;
            case "3-5" -> price.compareTo(million.multiply(new BigDecimal("3"))) > 0 &&
                    price.compareTo(million.multiply(new BigDecimal("5"))) <= 0;
            case "5-7" -> price.compareTo(million.multiply(new BigDecimal("5"))) > 0 &&
                    price.compareTo(million.multiply(new BigDecimal("7"))) <= 0;
            case "7-10" -> price.compareTo(million.multiply(new BigDecimal("7"))) > 0 &&
                    price.compareTo(million.multiply(new BigDecimal("10"))) <= 0;
            case "10-15" -> price.compareTo(million.multiply(new BigDecimal("10"))) > 0 &&
                    price.compareTo(million.multiply(new BigDecimal("15"))) <= 0;
            case "above15" -> price.compareTo(million.multiply(new BigDecimal("15"))) > 0;
            default -> true;
        };
    }
    private boolean filterByArea(RoomFilter room, String areaRange) {
        if (areaRange == null || areaRange.equals("all")) {
            return true;
        }
        // Lấy diện tích từ house object
         int area = room.getArea();

         return switch (areaRange) {
             case "under20" -> area < 20;
             case "20-30" -> area >= 20 && area <= 30;
             case "30-50" -> area > 30 && area <= 50;
             case "50-70" -> area > 50 && area <= 70;
             case "70-90" -> area > 70 && area <= 90;
             case "above90" -> area > 90;
             default -> true;
         };
    }
    RoomFilter converRoomtoRoomFilter (Room room) throws Exception {
        RoomFilter roomFilter = new RoomFilter();
        roomFilter.setId(room.getId());
        roomFilter.setName(room.getName());
        roomFilter.setPrice(room.getPrice());
        roomFilter.setArea(room.getArea());
        if (room.getImage() != null) {
            roomFilter.setImageBase64(Base64.getEncoder().encodeToString(room.getImage()));
        }
        roomFilter.setDescription(room.getDescription());
        roomFilter.setStatus(room.getStatus().getStatus());
        roomFilter.setHouse(room.getHouse());
        CommunesDto address = communesService.getCommunesById(room.getHouse().getIdCommune());
        roomFilter.setCommune(address);
        return roomFilter;
    }
}
