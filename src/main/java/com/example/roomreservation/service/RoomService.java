package com.example.roomreservation.service;

import com.example.roomreservation.exception.room.RoomNotFoundException;
import com.example.roomreservation.model.branch.Branch;
import com.example.roomreservation.model.branch.BranchName;
import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationTime;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.model.room.RoomDTO;
import com.example.roomreservation.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final BranchService branchService;

    private final ReservationService reservationService;

    private final ModelMapper modelMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository, BranchService branchService, ReservationService reservationService, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }




    public List<Room> getAllRooms(){
        List<Room>rooms= roomRepository.findAll();
        return rooms;
    }

    public List<Room> getAllAvailableRooms(ReservationTime reservationTime){
        LocalDateTime startTime=reservationTime.getStartTime();
        LocalDateTime endTime=reservationTime.getEndTime();
       List<Room> availableRooms= roomRepository.findAvailableRooms(startTime,endTime);
       if (availableRooms.isEmpty()){
           String message="There is no available rooms By this time";
           log.info(message);
       }
       return availableRooms;
    }

    public List<Room> getAllAvailableRoomsByBranchName(ReservationTime reservationTime, BranchName branchName){
        LocalDateTime startTime=reservationTime.getStartTime();
        LocalDateTime endTime=reservationTime.getEndTime();
        Branch branch =branchService.getBranchByName(branchName);
        List<Room> availableRooms
                = roomRepository.findAvailableRoomsByDateAndBranch(startTime,endTime,branch.getName());
        if (availableRooms.isEmpty()){
            String message="There is no available rooms By this time";
            log.info(message);
        }
        return availableRooms;
    }

    public Room getRoomById(Long id){
        Optional<Room> room=roomRepository.findById(id);
        if (!room.isPresent()){
            String message=String.format("the room with the id %s  is not found",id);
            log.info(message);
            throw new RoomNotFoundException(message);
        }
        return room.get();
    }

    public Room addRoom(RoomDTO room){
        Room newRoom=modelMapper.map(room,Room.class);
        Branch branch=branchService.getBranchById(newRoom.getBranch().getId());
        newRoom.setBranch(branch);
        newRoom= roomRepository.save(newRoom);
        log.info("created new room entity");
        return newRoom;
    }

    public Room updateRoom(RoomDTO updatedRoom,Long id){
        Room actualRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        Branch branch=branchService.getBranchById(updatedRoom.getBranch().getId());
        actualRoom.setBranch(branch);
        modelMapper.map(updatedRoom,actualRoom);
        return roomRepository.save(actualRoom);
    }

    public String deleteRoom(Long id){
        getRoomById(id);
        String message =String.format("Room with the id %s is deleted",id);
        roomRepository.deleteById(id);
       return message;
    }


    public boolean isRoomAvailable(Long roomId, LocalDateTime startTime,LocalDateTime endTime){
        Room room=getRoomById(roomId);
        List<Reservation>reservations=reservationService.getReservationByRoomAndEndTimeAfterAndStartTimeBefore(room,startTime,endTime);
        return reservations.isEmpty();
    }



}
