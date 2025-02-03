package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.BookingServiceDAO;
import com.example.momentous.momentous_finalproject.dto.BookingServiceDto;
import com.example.momentous.momentous_finalproject.entity.BookingService;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookingServiceDAOImpl implements BookingServiceDAO {
    public boolean saveBookingServiceList(ArrayList<BookingService> bookingServices) throws SQLException, ClassNotFoundException {
        for (BookingService entity : bookingServices) {
            try {
                boolean isOrderDetailSaved = saveBookingServiceDetails(entity);
                if (!isOrderDetailSaved) {
                    return false;
                }
            } catch (SQLException | ClassNotFoundException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveBookingServiceDetails(BookingService entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO BookingService (booking_id, service_id, date) VALUES (?, ?, ?)",  // Match the column names if needed
                entity.getBookingId(),
                entity.getServiceId(),
                entity.getBookingDate()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<BookingService> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BookingService entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(BookingService entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public BookingService findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
