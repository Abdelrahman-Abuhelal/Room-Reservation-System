package com.example.roomreservation.repository;


import com.example.roomreservation.model.reservation.Reservation;
import com.example.roomreservation.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {


   Optional<List<Reservation>> findByRoomAndEndTimeAfterAndStartTimeBefore(Room room, LocalDateTime startTime, LocalDateTime endTime);

}
