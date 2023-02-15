package com.example.roomreservation.controller;

import com.example.roomreservation.model.Room;
import com.example.roomreservation.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Room addedRoom = roomService.addRoom(room);
        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }


}
