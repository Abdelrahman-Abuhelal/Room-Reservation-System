package com.example.roomreservation.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "name")
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = "password")
    private String password;


}
