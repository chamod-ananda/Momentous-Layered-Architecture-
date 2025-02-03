package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.BOFactory;
import com.example.momentous.momentous_finalproject.bo.CompleteEventBO;
import com.example.momentous.momentous_finalproject.bo.ItemBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.EventDAOImpl;
import com.example.momentous.momentous_finalproject.dao.custom.impl.EventSupplierDAOImpl;
import com.example.momentous.momentous_finalproject.dao.custom.impl.ItemDAOImpl;
import com.example.momentous.momentous_finalproject.dao.custom.impl.SupplierDAOImpl;
import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.EventDto;
import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.entity.Event;
import com.example.momentous.momentous_finalproject.entity.EventSupplier;
import com.example.momentous.momentous_finalproject.entity.Item;
import com.example.momentous.momentous_finalproject.entity.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompleteEventBOImpl implements CompleteEventBO {
    SupplierBOImpl supplierBO = (SupplierBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    CreateBookingBOImpl createBookingBO = (CreateBookingBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CREATE_BOOKING);
    EventDAOImpl eventDAO = (EventDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EVENT);
    EventSupplierDAOImpl eventSupplierDAO = (EventSupplierDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EVENT_SUPPLIER);
    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.ITEM);
    ItemBO itemBo = new ItemBOImpl();
    SupplierDAOImpl supplierDAO = (SupplierDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.SUPPLIER);

    public ArrayList<String> loadSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierBO.getAllSupplierIds();

    }

    public String getNextEventId() throws SQLException, ClassNotFoundException {
        return eventDAO.getNextId();
    }

    public ArrayList<String> loadBookingIds() throws SQLException, ClassNotFoundException {
        return createBookingBO.loadAllBookingIds();
    }

    public ItemDto findByItemId(String selectedItemId) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.findById(selectedItemId);
        return new ItemDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDescription(),
                item.getCost(),
                item.getQuantity(),
                item.getSupplierId()
        );
    }

    public SupplierDto findBySupplierId(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.findById(selectedSupplierId);
        return new SupplierDto(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getEmail()
        );
    }

    public ArrayList<String> loadItemIDs(String itemId) throws SQLException, ClassNotFoundException {
        return itemBo.getAllItemIds(itemId);
    }

    @Override
    public boolean completeEventCreation(EventDto eventDto) throws SQLException, ClassNotFoundException {
        ArrayList<EventSupplier> eventSuppliers = (ArrayList<EventSupplier>) eventDto.toEventSupplierEntities();

        Event event = new Event(
                eventDto.getEventId(),
                eventDto.getBookingId(),
                eventDto.getEventType(),
                eventDto.getEventName(),
                eventDto.getBudget(),
                eventDto.getVenue(),
                eventDto.getDate(),
                eventSuppliers  // Use the converted list
        );

        Connection connection = DBConnection.getInstance().getConnection();

        if (connection == null) {
            throw new SQLException("Failed to obtain database connection.");
        }

        boolean result = false;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Save booking details
            result = eventDAO.save(event);
            if (!result) {
                connection.rollback(); // Rollback on failure
                return false;
            }

            // Save booking services
            boolean isSaved = eventSupplierDAO.saveEventSuppliersList(event.geteventSupplierList());
            if (!isSaved) {
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
        return createBookingBO.updateBookingStatusToUsed(bookingId);
    }
}
