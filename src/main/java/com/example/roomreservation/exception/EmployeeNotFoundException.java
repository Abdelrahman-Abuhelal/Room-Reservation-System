package com.example.roomreservation.exception;

import com.example.roomreservation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message){
        super(message);
    }
}
