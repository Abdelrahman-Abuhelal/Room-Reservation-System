package com.example.roomreservation.service;

import com.example.roomreservation.model.Room;
import com.example.roomreservation.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElseThrow();
    }

    public Room addRoom(Room room){
        Room addedRoom= roomRepository.save(room);
        log.info("created new room entity");
        return addedRoom;
    }
}
