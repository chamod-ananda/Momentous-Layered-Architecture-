package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.dto.BookingServiceDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookingServiceModel {
    public boolean saveBookingServiceList(ArrayList<BookingServiceDto> bookingServiceDtos) throws SQLException, ClassNotFoundException {
        for (BookingServiceDto bookingServiceDto : bookingServiceDtos) {
            try {
                boolean isOrderDetailSaved = saveBookingServiceDetails(bookingServiceDto);
                if (!isOrderDetailSaved) {
                    return false;
                }
            } catch (SQLException | ClassNotFoundException e) {
                return false;
            }
        }
        return true;
    }

    private boolean saveBookingServiceDetails(BookingServiceDto bookingServiceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO BookingService (booking_id, service_id, date) VALUES (?, ?, ?)",
                bookingServiceDto.getBookingId(),
                bookingServiceDto.getServiceId(),
                bookingServiceDto.getBookingDate()
        );
    }
}
