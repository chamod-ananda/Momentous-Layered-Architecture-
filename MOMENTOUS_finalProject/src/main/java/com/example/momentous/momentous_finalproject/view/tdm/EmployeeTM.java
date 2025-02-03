package com.example.momentous.momentous_finalproject.view.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeTM {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String jobRole;
    private Date joinedDate;
    private double salary;
    private String email;
    private String bookingId;
}
