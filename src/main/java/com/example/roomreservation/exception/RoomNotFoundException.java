package com.example.roomreservation.exception;

public class RoomNotFoundException extends RuntimeException{

    public RoomNotFoundException(String message){
        super(message);
    }
}
