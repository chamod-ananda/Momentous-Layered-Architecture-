package com.example.momentous.momentous_finalproject.dao;

import com.example.momentous.momentous_finalproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {
    String getNextId() throws SQLException, ClassNotFoundException;
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean save(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(String customerId) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    T findById(String selectedCustomerId) throws SQLException, ClassNotFoundException;
    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;
}
