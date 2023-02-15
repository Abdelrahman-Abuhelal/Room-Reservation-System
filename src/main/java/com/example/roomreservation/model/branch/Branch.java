package com.example.roomreservation.model.branch;


import com.example.roomreservation.model.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BranchName name;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Room> rooms;

}
