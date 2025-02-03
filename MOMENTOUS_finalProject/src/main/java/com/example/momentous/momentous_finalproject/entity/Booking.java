package com.example.momentous.momentous_finalproject.view;


import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
    private String bookingId;
    private String customerId;
    private int capacity;
    private String venue;
    private Date bookingDate;

    private ArrayList<BookingService> bookingServices;

}
