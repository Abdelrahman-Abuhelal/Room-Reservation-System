package com.example.roomreservation.service;

import com.example.roomreservation.exception.UserNotFoundException;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.model.user.UserDTO;
import com.example.roomreservation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(UserDTO user){
        User createdUser= modelMapper.map(user, User.class);
        return userRepository.save(createdUser);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public User updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        modelMapper.map(userDTO, user);
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
