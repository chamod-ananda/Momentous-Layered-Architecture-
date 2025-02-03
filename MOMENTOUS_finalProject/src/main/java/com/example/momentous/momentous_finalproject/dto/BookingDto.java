package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingDto {
    private String bookingId;
    private String customerId;
    private int capacity;
    private String venue;
    private Date bookingDate;

    private ArrayList<BookingServiceDto> bookingServiceDtos;
}
