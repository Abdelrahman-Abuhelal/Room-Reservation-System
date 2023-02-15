package com.example.roomreservation.exception;


public class ReservationNotFoundException extends RuntimeException{

   public ReservationNotFoundException(String message){
        super(message);
    }
}
