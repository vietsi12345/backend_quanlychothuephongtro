package com.doanthuctap.controller;

import com.doanthuctap.model.House;
import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomType;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.service.HouseService;
import com.doanthuctap.service.RoomService;
import com.doanthuctap.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private HouseRepository houseRepository;

    @PostMapping
    public ResponseEntity<Room> createRoom(
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam("houseId") Long houseId,
            @RequestParam("roomTypeId") Long roomTypeId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        House house =  houseRepository.findById(houseId).orElseThrow(() -> new Exception("House not found"));

        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);

        Room room = new Room();
        room.setPrice(price);
        room.setDescription(description);
        room.setAvailability("Còn trống");
        room.setHouse(house);
        room.setRoomType(roomType);

        Room createdRoom = roomService.createRoom(room, imageFile);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRoom () throws Exception {
        List<Room> allRoom = roomService.getAllRoom();
        return ResponseEntity.ok(allRoom);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Room> getRoomById(@PathVariable Long id) throws Exception {
        Room  room = roomService.getRoomById(id);
        return  ResponseEntity.ok(room);
    }

    @GetMapping("/by-house/{id}")
    public ResponseEntity <List<Room>> getRoomsByHouseID(@PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(roomService.getRoomsByHouseId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        Room updateRoom = roomService.updateRoom(id, price, description, imageFile);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteHouse (@PathVariable Long id) throws Exception {
        roomService.deleteRoom(id);
        return ResponseEntity.ok ("Xóa thành công phòng có id "+id);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity <Room> updateStatus(@PathVariable Long id,
                                              @RequestParam ("status") String status) throws Exception {
        Room  room = roomService.updateStatus(id,status);
        return  ResponseEntity.ok(room);
    }
}
