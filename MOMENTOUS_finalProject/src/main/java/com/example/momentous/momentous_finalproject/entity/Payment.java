package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Payment {
    private String paymentId;
    private Date paymentDate;
    private Double paymentAmount;
    private String bookingId;
}
