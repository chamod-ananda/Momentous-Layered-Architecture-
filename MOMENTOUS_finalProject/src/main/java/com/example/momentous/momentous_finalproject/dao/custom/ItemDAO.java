package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.entity.EventSupplier;
import com.example.momentous.momentous_finalproject.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException ;
    boolean reduceQty(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException;
}
