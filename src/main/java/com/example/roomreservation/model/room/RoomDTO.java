package com.example.roomreservation.model.room;

import com.example.roomreservation.model.branch.Branch;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoomDTO {

    private String name;

    private Branch branch;

    private Integer capacity;
    private Boolean isReserved;


}
