package com.example.roomreservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTResponseDTO> login (@RequestBody LoginRequestDTO loginRequest){

        JWTResponseDTO jwtResponseDto = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        return ResponseEntity.ok(jwtResponseDto);
    }
}
