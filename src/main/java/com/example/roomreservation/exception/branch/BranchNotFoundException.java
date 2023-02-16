package com.example.roomreservation.exception.branch;

public class BranchNotFoundException extends RuntimeException {

    public BranchNotFoundException(String message){
        super(message);
    }
}
