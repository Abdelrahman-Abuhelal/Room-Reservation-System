package com.example.roomreservation.exception.branch;


public class NoSuchBranchException extends RuntimeException{

    public NoSuchBranchException(String message){
        super(message);
    }
}
