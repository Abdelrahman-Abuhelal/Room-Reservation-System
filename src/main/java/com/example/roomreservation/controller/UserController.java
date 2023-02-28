package com.example.roomreservation.controller;

import com.example.roomreservation.model.user.User;
import com.example.roomreservation.model.user.UserDTO;
import com.example.roomreservation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList= userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    //get a user by its username
    @GetMapping
    public ResponseEntity<User> getUserByUsername(@RequestParam String username){
        User user= userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    // create a new user

}
