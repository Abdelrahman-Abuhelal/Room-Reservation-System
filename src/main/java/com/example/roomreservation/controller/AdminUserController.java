package com.example.roomreservation.controller;

import com.example.roomreservation.model.user.User;
import com.example.roomreservation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }


//        @PostMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> createAdminUser(@RequestBody UserDTO user) {
//        user.setRole(Role.ADMIN);
//        User createdUser = userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    //get All admin users
    @GetMapping
    public ResponseEntity<List<User>> getAllAdmins(){
        List<User> userList= userService.getAllAdmins();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }










}
