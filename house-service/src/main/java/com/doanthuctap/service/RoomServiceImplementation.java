package com.doanthuctap.service;

import com.doanthuctap.model.House;
import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomType;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.RoomDto;
import com.doanthuctap.response.RoomTypeDto;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomServiceImplementation implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Override
    public Room createRoom(Room room, MultipartFile imageFile) throws Exception {
        if (imageFile != null && !imageFile.isEmpty()) {
            room.setImage(imageFile.getBytes());
        }
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long id) throws Exception {
        return roomRepository.findById(id).orElseThrow(() -> new Exception("Room not found"));
    }

    @Override
    public List<Room> getAllRoom() throws Exception {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getRoomsByHouseId(Long houseId) throws Exception {
        return roomRepository.findByHouseId(houseId);
    }

    @Override
    public Room updateRoom(Long id, BigDecimal price, String description, MultipartFile imageFile) throws Exception {
        Room room = getRoomById(id);

        if (price != null  ) {
            room.setPrice(price);
        }
        if (description != null && !description.isEmpty()) {
            room.setDescription(description);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            room.setImage(imageFile.getBytes());
        }
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) throws Exception {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    @Override
    public Room updateStatus(Long id,String status) throws Exception {
        Room room = getRoomById(id);
        room.setAvailability(status);
        return roomRepository.save(room);
    }

    @Override
    public List<RoomTypeDto> getRoomTypesWithRoomsByHouseId(Long houseId) {
        // Lấy đối tượng House bằng houseId và kiểm tra xem nó có tồn tại không
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new RuntimeException("House not found"));

        // Lấy danh sách các phòng từ house
        List<Room> rooms = house.getRooms();

        // Tạo một Map để lưu trữ RoomType và các phòng tương ứng
        Map<RoomType, List<Room>> roomTypeMap = new HashMap<>();

        // Phân loại các phòng theo RoomType
        for (Room room : rooms) {
            RoomType roomType = room.getRoomType();
            roomTypeMap.computeIfAbsent(roomType, k -> new ArrayList<>()).add(room);
        }

        // Chuyển đổi Map thành danh sách RoomTypeDto
        return roomTypeMap.entrySet().stream()
                .map(entry -> convertToRoomTypeDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
    @Override
    public RoomTypeDto convertToRoomTypeDTO(RoomType roomType, List<Room> rooms) {
        RoomTypeDto dto = new RoomTypeDto();
        dto.setId(roomType.getId());
        dto.setName(roomType.getName());
        dto.setDescription(roomType.getDescription());

        // Chuyển đổi các phòng thành RoomDto và thêm vào RoomTypeDto
        List<RoomDto> roomDtos = rooms.stream()
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());
        dto.setRooms(roomDtos);

        return dto;
    }

    @Override
    public RoomDto convertToRoomDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setPrice(room.getPrice());
        dto.setDescription(room.getDescription());
        dto.setAvailability(room.getAvailability());
        if (room.getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(room.getImage()));
        }
        return dto;
    }
}
