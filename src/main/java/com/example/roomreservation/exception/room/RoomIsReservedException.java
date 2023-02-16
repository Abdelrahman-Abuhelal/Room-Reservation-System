package com.example.roomreservation.exception.room;

public class RoomIsReservedException extends RuntimeException{

    public RoomIsReservedException(String message){
        super(message);
    }
}
