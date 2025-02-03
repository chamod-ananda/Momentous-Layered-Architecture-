package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<BookingService> toBookingServiceEntities() {
        if (this.bookingServiceDtos == null) {
            return new ArrayList<>(); // Return an empty list if null
        }

        return this.bookingServiceDtos.stream()
                .map(dto -> new BookingService(
                        this.bookingId, // Assign the booking ID from BookingDto
                        dto.getServiceId(), // Get the service ID from BookingServiceDto
                        this.bookingDate
                ))
                .collect(Collectors.toList());
    }
}
