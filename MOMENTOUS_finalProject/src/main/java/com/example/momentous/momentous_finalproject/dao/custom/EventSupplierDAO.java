package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.entity.EventSupplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EventSupplierDAO extends CrudDAO<EventSupplier> {
    boolean saveEventSuppliersList(ArrayList<EventSupplier> eventSuppliersList) throws SQLException, ClassNotFoundException ;
    boolean saveEventSuppliers(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException ;
}
