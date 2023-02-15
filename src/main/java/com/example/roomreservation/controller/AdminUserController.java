package com.example.roomreservation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminUserController {

//        @PostMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> createAdminUser(@RequestBody UserDTO user) {
//        user.setRole(Role.ADMIN);
//        User createdUser = userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }
}
