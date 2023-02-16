package com.example.roomreservation.controller;

import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.model.room.RoomDTO;
import com.example.roomreservation.service.BranchService;
import com.example.roomreservation.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        Room room= roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> room= roomService.getAllRooms();
        return ResponseEntity.ok(room);
    }
    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody RoomDTO room){
        Room addedRoom = roomService.addRoom(room);
        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody RoomDTO room,@PathVariable Long id){
        Room updateRoom = roomService.updateRoom(room,id);
        return new ResponseEntity<>(updateRoom, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        String message = roomService.deleteRoom(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
