package com.example.roomreservation.repository;

import com.example.roomreservation.model.branch.BranchName;
import com.example.roomreservation.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT r FROM Room r WHERE r.isReserved = false AND r.id NOT IN " +
            "(SELECT rv.room.id FROM Reservation rv WHERE " +
            "(rv.startTime <= :endTime AND rv.endTime >= :startTime))")
    List<Room> findAvailableRooms(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);


    @Query("SELECT r FROM Room r WHERE r.isReserved = false AND r.branch.name= :branchName AND r.id NOT IN " +
            "(SELECT rv.room.id FROM Reservation rv WHERE " +
            "(rv.startTime <= :endTime AND rv.endTime >= :startTime))")
    List<Room> findAvailableRoomsByDateAndBranch(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime,@Param("branchName") BranchName branchName);







}