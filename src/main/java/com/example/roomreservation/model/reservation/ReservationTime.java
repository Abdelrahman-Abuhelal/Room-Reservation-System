package com.example.roomreservation.model.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationTime {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
