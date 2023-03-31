package com.example.roomreservation.model.reservation;

import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.model.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationDTO {

    private String reservationDescription;
    private Room room;
    private User owner;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
