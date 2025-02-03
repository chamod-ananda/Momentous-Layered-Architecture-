package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO{
    String getNextItemId() throws SQLException, ClassNotFoundException ;
    ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException ;
    ItemDto findById(String selectedItemId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException ;
    boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException ;
    boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException ;
    boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException ;
}
