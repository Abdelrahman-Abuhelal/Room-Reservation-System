//package com.example.roomreservation.controller;
//
//import com.example.roomreservation.model.user.User;
//import com.example.roomreservation.model.user.UserDTO;
//import com.example.roomreservation.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    //get all users NOT ADMIN
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers(){
//        List<User> userList= userService.getAllUsers();
//        return new ResponseEntity<>(userList, HttpStatus.OK);
//    }
//
//    //get a user by its id
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id){
//        User user= userService.getUserById(id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//    // create a new user
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
//        User createdUser= userService.createUser(user);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }
//
//    //update a user
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO user){
//        User updatedUser= userService.updateUser(id,user);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }
//
//    //delete an existed user
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id){
//        String message=userService.deleteUser(id);
//        return new ResponseEntity<>(message,HttpStatus.OK);
//    }
//}
