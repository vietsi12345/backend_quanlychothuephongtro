package com.doanthuctap.controller;


import com.doanthuctap.model.RoomType;
import com.doanthuctap.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/roomTypes")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<RoomType> createRoomType(
            @RequestParam("name") String name,
            @RequestParam("description") String description) throws Exception {

        RoomType roomType = new RoomType();
        roomType.setName(name);
        roomType.setDescription(description);

        RoomType createdRoomType = roomTypeService.createRoomType(roomType);
        return ResponseEntity.ok(createdRoomType);
    }

    @GetMapping
    public ResponseEntity<List<RoomType>>  getAllRoomType () throws Exception {
        List<RoomType> allRoomType = roomTypeService.getAllRoomType();
        return ResponseEntity.ok(allRoomType);
    }

    @GetMapping("/{id}")
    public ResponseEntity <RoomType> getRoomTypeById(@PathVariable Long id) throws Exception {
        RoomType  roomType = roomTypeService.getRoomTypeById(id);
        return  ResponseEntity.ok(roomType);
    }

    @GetMapping("/test")
    public ResponseEntity <List<RoomType>> test () throws Exception {
        List<RoomType>  roomTypes = roomTypeService.getAllRoomType();
        return  ResponseEntity.ok(roomTypes);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoomType> updateRoomType(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description) throws Exception {

        RoomType updateRoomType = roomTypeService.updateRoomType(id, name, description);
        return ResponseEntity.ok(updateRoomType);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteRoomType (@PathVariable Long id) throws Exception {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.ok ("Xóa thành công kiểu phòng có id "+id);
    }

}
