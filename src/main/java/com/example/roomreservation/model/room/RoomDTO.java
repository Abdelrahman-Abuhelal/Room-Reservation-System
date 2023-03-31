package com.example.roomreservation.model.room;

import com.example.roomreservation.model.branch.Branch;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoomDTO {

    private String name;
    @NotBlank
    private Branch branch;

    private Integer capacity;

    public RoomDTO(){

    }
}
