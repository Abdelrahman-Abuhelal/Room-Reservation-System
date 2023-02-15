package com.example.roomreservation.repository;

import com.example.roomreservation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
