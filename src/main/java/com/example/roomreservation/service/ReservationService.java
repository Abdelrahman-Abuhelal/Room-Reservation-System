package com.example.roomreservation.service;

import com.example.roomreservation.exception.reservation.InvalidReservationTimeException;
import com.example.roomreservation.exception.reservation.ReservationNotFoundException;
import com.example.roomreservation.exception.room.RoomIsReservedException;
import com.example.roomreservation.exception.room.RoomNotFoundException;
import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationDTO;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomService roomService;

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, @Lazy RoomService roomService, UserService userService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    public Reservation createReservation(ReservationDTO reservation){
        Room reservedRoom=roomService.getRoomById(reservation.getRoom().getId());
        User user=userService.getUserById(reservation.getOwner().getId());
        LocalDateTime startTime=reservation.getStartTime();
        LocalDateTime endTime=reservation.getEndTime();
        if (startTime.isAfter(endTime)){
            throw new InvalidReservationTimeException("Start time cannot be after end time");
        }
        if (!roomService.isRoomAvailable(reservedRoom.getId(),reservation.getStartTime(),reservation.getEndTime())){
            throw new RoomIsReservedException("Sorry! The room is reserved at that time");
        }
        reservedRoom.setIsReserved(true);
        reservation.setOwner(user);
        reservation.setRoom(reservedRoom);
        Reservation reservation1=modelMapper.map(reservation,Reservation.class);
        return reservationRepository.save(reservation1);
    }



    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationByRoomAndEndTimeAfterAndStartTimeBefore(Room room, LocalDateTime startTime,LocalDateTime endTime){
        String exceptionMessage="Reservation with the id %s not found";
        return reservationRepository.findByRoomAndEndTimeAfterAndStartTimeBefore(room,startTime,endTime).orElseThrow(()->  new RoomNotFoundException(String.format(exceptionMessage,room.getId())));
    }

    public Reservation getReservationById(Long id){
        String exceptionMessage="Reservation with the id %s not found";
        return reservationRepository.findById(id).orElseThrow(()-> new ReservationNotFoundException(String.format(exceptionMessage,id)));
    }

    public Reservation updateReservation(Long id,ReservationDTO reservation){
        Reservation returnedReservation=getReservationById(id);
        modelMapper.map(reservation,returnedReservation);
        return reservationRepository.save(returnedReservation);
    }

  //mustn't be deleted it should hide the visibility Only
    public Long deleteReservation(Long id){
        Reservation reservation=getReservationById(id);
        reservationRepository.deleteById(id);
        log.info("reservation with the id %s is deleted",id);
        return reservation.getId();
    }

}
