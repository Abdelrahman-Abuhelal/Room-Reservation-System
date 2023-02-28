package com.example.roomreservation.controller;


import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationDTO;
import com.example.roomreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //getting all reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservationList= reservationService.getAllReservations();
        return new ResponseEntity<>(reservationList,HttpStatus.OK);
    }

    //get a reservation by the id of the reservation
    @GetMapping({"/{id}"})
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        Reservation reservationList= reservationService.getReservationById(id);
        return new ResponseEntity<>(reservationList,HttpStatus.OK);
    }

    // reserve a room
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationDTO reservation){
        Reservation createdReservation= reservationService.createReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }


    // update one reservation with new data
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,@RequestBody @Valid  ReservationDTO reservation){
       Reservation updatedReservation= reservationService.updateReservation(id,reservation);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }


    // delete a reservation by its id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
       Long deletedId =reservationService.deleteReservation(id);
       String message="The reservation with the id "+deletedId+" is deleted";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}

