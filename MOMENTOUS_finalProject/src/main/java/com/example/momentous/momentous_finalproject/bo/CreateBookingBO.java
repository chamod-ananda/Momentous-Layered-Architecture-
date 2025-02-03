package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.BookingDto;
import com.example.momentous.momentous_finalproject.dto.ServiceDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CreateBookingBO extends SuperBO{
    ArrayList<String> loadServiceIds() throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException ;
    String getNextBookingId() throws SQLException, ClassNotFoundException ;
    ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadAllBookingIds() throws SQLException, ClassNotFoundException ;
    boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException ;
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException ;
}
