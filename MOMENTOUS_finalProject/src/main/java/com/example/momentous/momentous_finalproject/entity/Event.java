package com.example.momentous.momentous_finalproject.entity;

import com.example.momentous.momentous_finalproject.dto.EventSupplierDto;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Event {
    private String eventId;
    private String bookingId;
    private String eventType;
    private String eventName;
    private Double budget;
    private String venue;
    private Date date;

    private ArrayList<EventSupplier> eventSupplierList;

    public ArrayList<EventSupplier> getEventSuppliersList() {
        return null;
    }
}
