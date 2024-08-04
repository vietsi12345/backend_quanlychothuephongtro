package com.doanthuctap.service;

import com.doanthuctap.model.RoomType;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeServiceImplementation implements RoomTypeService{
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Override
    public RoomType createRoomType(RoomType roomType) throws Exception {
        return roomTypeRepository.save(roomType) ;
    }

    @Override
    public RoomType getRoomTypeById(Long id) throws Exception {
        return roomTypeRepository.findById(id).orElseThrow(() -> new Exception("RoomType not found"));
    }

    @Override
    public List<RoomType> getAllRoomType() throws Exception {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType updateRoomType(Long id, String name, String description) throws Exception {
        RoomType roomType = getRoomTypeById(id);
        if (name != null && !name.isEmpty()) {
            roomType.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            roomType.setDescription(description);
        }
        return roomTypeRepository.save(roomType);
    }

    @Override
    public void deleteRoomType(Long id) throws Exception {
        RoomType roomType = getRoomTypeById(id);
        roomTypeRepository.delete(roomType);
    }
}
