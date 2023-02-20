package com.example.roomreservation.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginRequestDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
