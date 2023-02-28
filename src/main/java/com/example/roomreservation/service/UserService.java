package com.example.roomreservation.service;

import com.example.roomreservation.exception.user.UserNotFoundException;
import com.example.roomreservation.model.user.Role;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.model.user.UserDTO;
import com.example.roomreservation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService  {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        List<User>users= userRepository.findAll();
        if (users.isEmpty()){
            throw new UserNotFoundException("There is no Users in the System");
        }
        return users;
    }

    public User findByUsername(String username) {
        Optional<User> myuser = userRepository.findUserByUsername(username);
        if (!myuser.isPresent()){
            throw new UserNotFoundException(String.format("the user with username %s is not found",username));
        }return myuser.get();
    }

    public Boolean isPresentUsername(String username) {
        Optional<User> myuser = userRepository.findUserByUsername(username);
        if (!myuser.isPresent()){
           return false;
        }return true;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
//        // I am having only one role here.
//        // In case I need more than one role :
//        // I can modify it to have an authority list attribute instead of just role
//        return new com.example.roomreservation.model.user.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRole());
//    }
}
