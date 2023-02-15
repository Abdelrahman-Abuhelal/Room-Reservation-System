package com.example.roomreservation.service;

import com.example.roomreservation.exception.ReservationNotFoundException;
import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationDTO;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomService roomService;
    private final ModelMapper modelMapper;


    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }


    public Reservation createReservation(ReservationDTO reservation){
        Room reservedRoom=roomService.getRoomById(reservation.getRoomId());
//        if (reservedRoom.getIsReserved()){
//
//        }
        reservedRoom.setIsReserved(true);
        Reservation reservation1=modelMapper.map(reservation,Reservation.class);
        return reservationRepository.save(reservation1);
    }
    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id){
        String exceptionMessage="Reservation with the id %s not found";
        return reservationRepository.findById(id).orElseThrow(()-> new ReservationNotFoundException(String.format(exceptionMessage,id)));
    }

    public Reservation updateReservation(Long id,Reservation reservation){
        Reservation returnedReservation=getReservationById(id);
        modelMapper.map(reservation,returnedReservation);
        return reservationRepository.save(returnedReservation);
    }

  //mustn't be deleted it should hide the visibilty Only
    public Long deleteReservation(Long id){
        Reservation reservation=getReservationById(id);
        reservationRepository.deleteById(id);
        log.info("reservation with the id %s is deleted",id);
        return reservation.getId();
    }

}
