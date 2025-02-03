package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerId;
    private String customerTitle;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private Date regDate;

    public String concatName() {
        return firstName + " " + lastName;
    }
}
