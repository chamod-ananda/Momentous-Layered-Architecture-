package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.entity.BookingService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingServiceDAO extends CrudDAO<BookingService> {
    boolean saveBookingServiceList(ArrayList<BookingService> bookingServices) throws SQLException, ClassNotFoundException ;

    boolean saveBookingServiceDetails(BookingService bookingServiceDto) throws SQLException, ClassNotFoundException ;
}
