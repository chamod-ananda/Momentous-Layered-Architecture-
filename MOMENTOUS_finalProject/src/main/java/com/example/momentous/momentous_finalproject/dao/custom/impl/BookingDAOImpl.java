package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.BookingDAO;
import com.example.momentous.momentous_finalproject.dao.custom.BookingServiceDAO;
import com.example.momentous.momentous_finalproject.util.CrudUtil;
import com.example.momentous.momentous_finalproject.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    BookingServiceDAO bookingServiceDAO = new BookingServiceDAOImpl();

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }

    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking order by booking_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i + 1;
            return String.format("B%03d", newIndex);
        }
        return "B001";
    }

    public boolean save(Booking entity) throws SQLException, ClassNotFoundException {


        boolean isBookingSaved = CrudUtil.execute(
                "INSERT INTO booking (booking_id, customer_id, capacity, venue, booking_date) VALUES (?, ?, ?, ?, ?)",
                entity.getBookingId(),
                entity.getCustomerId(),
                entity.getCapacity(),
                entity.getVenue(),
                entity.getBookingDate()
        );
        return isBookingSaved;
    }

    @Override
    public boolean delete(String bookingId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Booking entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Booking> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE booking SET status = ? WHERE booking_id = ?", "used", bookingId);

    }

    public ArrayList<String> getAvailableBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT booking_id FROM booking WHERE status = 'available'");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }
}
