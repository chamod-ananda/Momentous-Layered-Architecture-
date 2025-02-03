package com.example.momentous.momentous_finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserTM {
    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
