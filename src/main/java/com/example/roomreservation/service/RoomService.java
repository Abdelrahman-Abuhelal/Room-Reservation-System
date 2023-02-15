package com.example.roomreservation.service;

import com.example.roomreservation.exception.RoomNotFoundException;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElseThrow();
    }


    public List<Room> getAllRooms(){
        List<Room>rooms= roomRepository.findAll();
        return rooms;
    }

    public Room addRoom(Room room){
        Room addedRoom= roomRepository.save(room);
        log.info("created new room entity");
        return addedRoom;
    }

    public Room updateRoom(Room updatedRoom,Long id){
        Room actualRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id " + id));
        actualRoom.setName(updatedRoom.getName());
        actualRoom.setCapacity(updatedRoom.getCapacity());
        actualRoom.setIsReserved(updatedRoom.getIsReserved());
        return roomRepository.save(actualRoom);
    }

    public Long deleteRoom(Long id){
        Room actualRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id " + id));
       roomRepository.delete(actualRoom);
       return actualRoom.getId();
    }
}
