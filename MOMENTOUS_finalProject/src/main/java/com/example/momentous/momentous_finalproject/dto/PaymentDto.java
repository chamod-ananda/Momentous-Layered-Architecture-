package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDto {
    private String paymentId;
    private Date paymentDate;
    private Double paymentAmount;
    private String bookingId;
}
