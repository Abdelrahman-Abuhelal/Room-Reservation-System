package com.example.roomreservation.repository;

import com.example.roomreservation.model.user.Role;
import com.example.roomreservation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserByUsername(String username);

//    Optional<List<User>> findUsersByRole(Role role);
}
