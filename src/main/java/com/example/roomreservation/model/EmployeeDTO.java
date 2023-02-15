package com.example.roomreservation.model;

import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.Email;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmployeeDTO {

    private String name;
    @Email
    private String email;
    private String password;
}
