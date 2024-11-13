package com.doanthuctap.controller;

import com.doanthuctap.model.House;
import com.doanthuctap.model.Room;
import com.doanthuctap.model.RoomStatus;
import com.doanthuctap.repository.HouseRepository;
import com.doanthuctap.service.HouseService;
import com.doanthuctap.service.RoomService;
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
    private HouseRepository houseRepository;

    @PostMapping
    public ResponseEntity<Room> createRoom(
            @RequestParam("price") BigDecimal price,
            @RequestParam("area") int area,
            @RequestParam("description") String description,
            @RequestParam("houseId") Long houseId,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        House house =  houseRepository.findById(houseId).orElseThrow(() -> new Exception("House not found"));


        Room room = new Room();
        room.setPrice(price);
        room.setDescription(description);
        room.setStatus(RoomStatus.AVAILABLE);
        room.setHouse(house);
        room.setName(name);
        room.setArea(area);

        Room createdRoom = roomService.createRoom(room, imageFile);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping ("/all-customer")
    public ResponseEntity<List<Room>> getAllRoomCustomer () throws Exception {
        List<Room> allRoom = roomService.getAllRoomForCustomer();
        return ResponseEntity.ok(allRoom);
    }

    @GetMapping ("/all-admin")
    public ResponseEntity<List<Room>> getAllRoomAdmin () throws Exception {
        List<Room> allRoom = roomService.getAllRoomForAdmin();
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
    @GetMapping("/by-house/admin/{id}")
    public ResponseEntity <List<Room>> getRoomsByHouseIDForAdmin(@PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(roomService.getRoomsByHouseIdForAdmin(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "area", required = false) Integer area,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) throws Exception {

        Room updateRoom = roomService.updateRoom(id, price, description, area, name,imageFile);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Room> deleteHouse (@PathVariable Long id) throws Exception {
        Room room=  roomService.deleteRoom(id);
        return ResponseEntity.ok (room);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity <Room> updateStatus(@PathVariable Long id,
                                              @RequestParam ("status") RoomStatus status) throws Exception {
        Room  room = roomService.updateStatus(id,status);
        return  ResponseEntity.ok(room);
    }
}
