package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class BookingServiceDto {
    private String bookingId;
    private String serviceId;
    private Date bookingDate;
}
