package com.example.roomreservation.repository;

import com.example.roomreservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {


}
