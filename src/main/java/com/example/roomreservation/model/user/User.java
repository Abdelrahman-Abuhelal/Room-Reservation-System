package com.example.roomreservation.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {



    @Column(name = "username")
    @NotBlank
    @Id
    private String username;



    @Column(name = "email")
    @NotBlank
    @Size(max = 50)
    private String email;

    @Column(name = "locked")
    private Boolean locked = false;

    @Column(name = "enabled")
    private Boolean enabled = true;


    public User(String username,String email) {
        this.username = username;
        this.email=email;
    }



}
