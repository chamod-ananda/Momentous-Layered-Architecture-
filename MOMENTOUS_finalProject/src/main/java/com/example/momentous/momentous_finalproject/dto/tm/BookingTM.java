package com.example.momentous.momentous_finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingTM {
    private String bookingId;
    private String customerId;
    private String serviceId;
    private int capacity;
    private String venue;
    private Date bookingDate;
    private Button action;
}
