package com.example.roomreservation.controller;

import com.example.roomreservation.model.room.Room;
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
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Room addedRoom = roomService.addRoom(room);
        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room,@PathVariable Long id){
        Room updateRoom = roomService.updateRoom(room,id);
        return new ResponseEntity<>(updateRoom, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        Long deletedId = roomService.deleteRoom(id);
        String message = "Room with the id "+deletedId+" is deleted";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
