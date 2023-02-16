package com.example.roomreservation.exception.reservation;

public class InvalidReservationTimeException extends RuntimeException{

    public InvalidReservationTimeException(String message){
        super(message);
    }
}
