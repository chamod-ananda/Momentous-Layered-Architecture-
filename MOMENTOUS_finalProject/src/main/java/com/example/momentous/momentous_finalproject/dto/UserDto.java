package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
