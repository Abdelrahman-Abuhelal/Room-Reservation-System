package com.example.roomreservation.model.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationDTO {

    private String reservationDescription;
    private Long roomId;
    private Long ownerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
