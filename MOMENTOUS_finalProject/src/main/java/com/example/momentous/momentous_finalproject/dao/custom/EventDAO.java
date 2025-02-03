package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.entity.Event;

import java.sql.SQLException;

public interface EventDAO extends CrudDAO<Event> {
    String getNextId() throws SQLException, ClassNotFoundException ;
    boolean save(Event entity) throws SQLException, ClassNotFoundException;
}
