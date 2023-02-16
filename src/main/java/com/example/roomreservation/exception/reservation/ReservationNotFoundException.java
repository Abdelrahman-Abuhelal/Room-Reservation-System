package com.example.roomreservation.exception.reservation;


public class ReservationNotFoundException extends RuntimeException{

   public ReservationNotFoundException(String message){
        super(message);
    }
}
