package com.example.roomreservation.controller;


import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.reservation.ReservationDTO;
import com.example.roomreservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservationList= reservationService.getAllReservations();
        return new ResponseEntity<>(reservationList,HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        Reservation reservationList= reservationService.getReservationById(id);
        return new ResponseEntity<>(reservationList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservation){
        Reservation createdReservation= reservationService.createReservation(reservation);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,@RequestBody Reservation reservation){
       Reservation updatedReservation= reservationService.updateReservation(id,reservation);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
       Long deletedId =reservationService.deleteReservation(id);
       String message="The reservation with the id "+deletedId+" is deleted";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}

