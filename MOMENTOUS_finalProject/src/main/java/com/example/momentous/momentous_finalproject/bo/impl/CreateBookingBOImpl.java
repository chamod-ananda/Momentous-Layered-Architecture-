package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.BOFactory;
import com.example.momentous.momentous_finalproject.bo.CreateBookingBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.BookingDAOImpl;
import com.example.momentous.momentous_finalproject.dao.custom.impl.BookingServiceDAOImpl;
import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.BookingDto;
import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.entity.BookingService;
import com.example.momentous.momentous_finalproject.entity.Booking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateBookingBOImpl implements CreateBookingBO {
    BookingServiceDAOImpl bookingServiceDAO = (BookingServiceDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.BOOKING_SERVICE);
    BookingDAOImpl bookingDAO = (BookingDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.BOOKING);
    ServiceBOImpl serviceBO = (ServiceBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICE);
    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    public ArrayList<String> loadServiceIds() throws SQLException, ClassNotFoundException {
        return serviceBO.getAllServiceIds();
    }

    public ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        return customerBO.getAllCustomerIds();
    }

    public ArrayList<String> loadAllBookingIds() throws SQLException, ClassNotFoundException {
        return bookingDAO.getAllIds();
    }

    public String getNextBookingId() throws SQLException, ClassNotFoundException {
        return bookingDAO.getNextId();
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        return serviceBO.findById(selectedServiceId);

    }

    public boolean saveBooking(BookingDto bookingDto) throws SQLException, ClassNotFoundException {
        // Convert BookingServiceDtos to BookingService entities
        ArrayList<BookingService> bookingServices = (ArrayList<BookingService>) bookingDto.toBookingServiceEntities();

        // Create Booking entity
        Booking booking = new Booking(
                bookingDto.getBookingId(),
                bookingDto.getCustomerId(),
                bookingDto.getCapacity(),
                bookingDto.getVenue(),
                bookingDto.getBookingDate(),
                bookingServices
        );

        // Get database connection
        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Save booking details
            result = bookingDAO.save(booking);
            if (!result) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Save booking services
            boolean isBookingServicesSaved = bookingServiceDAO.saveBookingServiceList(booking.getBookingServices());
            if (!isBookingServicesSaved) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Commit transaction if everything is successful
            connection.commit();
            result = true;
        } catch (Exception e) {
            connection.rollback(); // Rollback in case of an exception
            throw new SQLException("Error saving booking: " + e.getMessage(), e);
        } finally {
            // Reset auto-commit mode
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        return result;
    }

    public boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.updateBookingStatusToUsed(bookingId);
    }
}
