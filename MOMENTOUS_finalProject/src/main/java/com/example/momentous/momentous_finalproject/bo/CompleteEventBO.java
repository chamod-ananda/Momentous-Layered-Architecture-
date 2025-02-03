package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.EventDto;
import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompleteEventBO extends SuperBO{
    boolean completeEventCreation(EventDto eventDto) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadSupplierIds() throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadBookingIds() throws SQLException, ClassNotFoundException ;
    ItemDto findByItemId(String itemId) throws SQLException, ClassNotFoundException ;
    SupplierDto findBySupplierId(String supplierId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> loadItemIDs(String supplierId) throws SQLException, ClassNotFoundException ;
    String getNextEventId() throws SQLException, ClassNotFoundException ;
    boolean updateBookingStatusToUsed(String bookingId) throws SQLException, ClassNotFoundException;
}
