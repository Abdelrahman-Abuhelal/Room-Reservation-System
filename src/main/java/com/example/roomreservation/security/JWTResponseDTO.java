package com.example.roomreservation.security;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JWTResponseDTO {
    private String accessToken;
    private String refreshToken;
}
