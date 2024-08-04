package com.doanthuctap.service;

import com.doanthuctap.model.RoomType;


import java.util.List;

public interface RoomTypeService {
    RoomType createRoomType(RoomType roomType) throws Exception;

    RoomType getRoomTypeById (Long id) throws  Exception;

    List<RoomType> getAllRoomType () throws  Exception;

    RoomType updateRoomType (Long id, String name, String description) throws  Exception;

    void deleteRoomType(Long id) throws  Exception;

}
