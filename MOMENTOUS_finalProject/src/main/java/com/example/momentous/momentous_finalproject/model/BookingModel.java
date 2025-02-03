package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.BookingDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingModel {
    private final BookingServiceModel bookingServiceModel = new BookingServiceModel();

    public ArrayList<String> getAllBookingIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select booking_id from booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }

    public String getNextBookingId() throws SQLException {
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

    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        try {
            connection.setAutoCommit(false);

            boolean isBookingSaved = CrudUtil.execute(
                    "INSERT INTO booking VALUES (?, ?, ?, ?, ?)",
                    bookingDto.getBookingId(),
                    bookingDto.getCustomerId(),
                    bookingDto.getCapacity(),
                    bookingDto.getVenue(),
                    bookingDto.getBookingDate()
            );

            if (isBookingSaved) {
                boolean isOrderDetailListSaved = bookingServiceModel.saveBookingServiceList(bookingDto.getBookingServiceDtos());
                if (isOrderDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;

        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Failed to save booking transaction", e);

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }
}
