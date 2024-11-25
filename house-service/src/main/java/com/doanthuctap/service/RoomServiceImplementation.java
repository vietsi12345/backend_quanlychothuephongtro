package com.doanthuctap.service;

import com.doanthuctap.model.House;
import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomStatus;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.repository.RoomRepository;
import com.doanthuctap.response.RoomDto;
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
    public List<Room> getAllRoomForCustomer() throws Exception {
        return roomRepository.findByStatusNot(RoomStatus.DELETED);
    }

    @Override
    public List<Room> getAllRoomForAdmin() throws Exception {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getRoomsByHouseId(Long houseId) throws Exception {
        return roomRepository.findByHouseIdAndStatusNot(houseId,RoomStatus.DELETED);
    }

    @Override
    public Room updateRoom(Long id, BigDecimal price, String description,Integer  area,String name, MultipartFile imageFile) throws Exception {
        Room room = getRoomById(id);

        if (price != null  ) {
            room.setPrice(price);
        }
        if (description != null && !description.isEmpty()) {
            room.setDescription(description);
        }
        if (area != null ) {
            room.setArea(area);
        }
        if (name != null && !name.isEmpty()) {
            room.setName(name);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            room.setImage(imageFile.getBytes());
        }
        return roomRepository.save(room);
    }

    @Override
    public Room deleteRoom(Long id) throws Exception {
        Room room = getRoomById(id);
        room.setStatus(RoomStatus.DELETED);
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room updateStatus(Long id,RoomStatus status) throws Exception {
        Room room = getRoomById(id);
        room.setStatus(status);
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getRoomsByHouseIdForAdmin(Long houseId) throws Exception {
        return roomRepository.findByHouseId(houseId);
    }

    @Override
    public Integer getExistContractOfRoom(Long roomId) throws Exception {
        Room room = getRoomById(roomId);
        if (room.getStatus().equals(RoomStatus.RENTED)){
            return null;
        } else return null;

    }
}
