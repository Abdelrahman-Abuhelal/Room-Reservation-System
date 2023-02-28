package com.example.roomreservation.model.reservation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReservationTime {

    @NotBlank
    private LocalDateTime startTime;
    @NotBlank
    private LocalDateTime endTime;
}
