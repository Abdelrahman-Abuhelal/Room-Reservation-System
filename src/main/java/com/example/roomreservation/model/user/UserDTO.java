package com.example.roomreservation.model.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    private Role role;
    private String username;
    private String email;
    private String password;
}
