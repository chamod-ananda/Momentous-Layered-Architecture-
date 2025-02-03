package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String jobRole;
    private Date joinedDate;
    private Double salary;
    private String email;
    private String bookingId;
}
