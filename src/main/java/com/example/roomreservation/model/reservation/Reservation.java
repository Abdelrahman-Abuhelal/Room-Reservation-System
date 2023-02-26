package com.example.roomreservation.model.reservation;

        import com.example.roomreservation.model.room.Room;
        import com.example.roomreservation.model.user.User;
        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.ToString;

        import java.time.LocalDateTime;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationDescription;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User owner;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;









}
