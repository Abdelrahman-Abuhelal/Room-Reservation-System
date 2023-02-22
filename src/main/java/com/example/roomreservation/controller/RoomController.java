package com.example.roomreservation.controller;

import com.example.roomreservation.model.branch.BranchName;
import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationTime;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.model.room.RoomDTO;
import com.example.roomreservation.service.BranchService;
import com.example.roomreservation.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //getting a room by the id
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        Room room= roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }


    //getting all rooms
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> room= roomService.getAllRooms();
        return ResponseEntity.ok(room);
    }

    //getting a list of available rooms by a specific time
    @GetMapping("/available")
    public ResponseEntity<List<Room>>getAllAvailableRooms(@RequestBody ReservationTime reservationTime){
        List<Room> availableRooms=roomService.getAllAvailableRooms(reservationTime);
        return ResponseEntity.ok(availableRooms);
    }


    //getting all available room in a specified branch
    @GetMapping("/{branchName}/available")
    public ResponseEntity<List<Room>>getAllAvailableRooms(@RequestBody ReservationTime reservationTime,@PathVariable String branchName){
        BranchName name=BranchName.valueOf(branchName.toUpperCase());
        List<Room> availableRooms=roomService.getAllAvailableRoomsByBranchName(reservationTime,name);
        return ResponseEntity.ok(availableRooms);
    }

    //adding a new room
    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody RoomDTO room){
        Room addedRoom = roomService.addRoom(room);
        return new ResponseEntity<>(addedRoom, HttpStatus.CREATED);
    }

    //update a specific room with a new data
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody RoomDTO room,@PathVariable Long id){
        Room updateRoom = roomService.updateRoom(room,id);
        return new ResponseEntity<>(updateRoom, HttpStatus.OK);
    }

    //delete one room by its id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        String message = roomService.deleteRoom(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
