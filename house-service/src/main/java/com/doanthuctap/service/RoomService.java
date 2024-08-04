package com.doanthuctap.service;

import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomType;
import com.doanthuctap.response.RoomDto;
import com.doanthuctap.response.RoomTypeDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room, MultipartFile imageFile) throws Exception;

    Room getRoomById (Long id) throws  Exception;

    List<Room> getAllRoom () throws  Exception;

    List<Room> getRoomsByHouseId (Long houseId) throws  Exception;

    Room updateRoom (Long id, BigDecimal price, String description, MultipartFile imageFile) throws  Exception;

    void deleteRoom (Long id) throws  Exception;

    Room updateStatus (Long id,String status) throws Exception;

    List<RoomTypeDto> getRoomTypesWithRoomsByHouseId (Long houseId);

    RoomTypeDto convertToRoomTypeDTO(RoomType roomType,List<Room> rooms);

    RoomDto convertToRoomDTO(Room room);
}
