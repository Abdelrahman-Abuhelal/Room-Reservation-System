package com.example.roomreservation.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
//    private final LDAPServiceImpl ldapService;


    @PostMapping("/login")
    public String login (@RequestBody LoginRequestDTO loginRequest) throws Exception {
        String jwtResponseDto;
    try{
         jwtResponseDto = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        log.debug("success");
    }catch (BadCredentialsException e) {
        throw new Exception("Incorrect username or password", e);
    }
        return jwtResponseDto;
    }
}
