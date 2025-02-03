package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {
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
