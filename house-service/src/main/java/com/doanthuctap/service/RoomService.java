package com.doanthuctap.service;

import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomStatus;
import com.doanthuctap.response.RoomDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room, MultipartFile imageFile) throws Exception;

    Room getRoomById (Long id) throws  Exception;

    List<Room> getAllRoomForCustomer () throws  Exception;

    List<Room> getAllRoomForAdmin () throws  Exception;

    List<Room> getRoomsByHouseId (Long houseId) throws  Exception;

    Room updateRoom (Long id, BigDecimal price, String description,Integer  area,String name, MultipartFile imageFile) throws  Exception;

    Room deleteRoom (Long id) throws  Exception;

    Room updateStatus (Long id, RoomStatus status) throws Exception;

    List<Room> getRoomsByHouseIdForAdmin (Long houseId) throws  Exception;

    // lấy thời gian còn lại của hợp đồng
    Integer getExistContractOfRoom (Long roomId) throws Exception;
}
