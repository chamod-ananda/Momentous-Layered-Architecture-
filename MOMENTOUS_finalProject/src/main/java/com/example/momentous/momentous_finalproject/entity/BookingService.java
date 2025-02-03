package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class BookingService {
    private String bookingId;
    private String serviceId;
    private Date bookingDate;
}
