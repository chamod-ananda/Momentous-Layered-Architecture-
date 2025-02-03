package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class EventDto {
    private String eventId;
    private String bookingId;
    private String eventType;
    private String eventName;
    private Double budget;
    private String venue;
    private Date date;

    private ArrayList<EventSupplierDto> eventSupplierDtos;
}
