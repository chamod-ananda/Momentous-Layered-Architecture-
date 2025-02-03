package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.view.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDAO extends CrudDAO<Booking> {
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException ;
}
