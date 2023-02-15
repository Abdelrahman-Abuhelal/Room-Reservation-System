package com.example.roomreservation.exception;

public class BranchNotFoundException extends RuntimeException {

    public BranchNotFoundException(String message){
        super(message);
    }
}
