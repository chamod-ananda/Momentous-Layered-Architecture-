package com.example.momentous.momentous_finalproject.view.tdm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {
    private String customerId;
    private String customerTitle;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private Date regDate;
}
