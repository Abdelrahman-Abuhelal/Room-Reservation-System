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
@NoArgsConstructor
public class User {



    @NotBlank
    @Id
    private String username;



    @NotBlank
    @Size(max = 50)
    private String email;



    public User(String username,String email) {
        this.username = username;
        this.email=email;
    }



}
