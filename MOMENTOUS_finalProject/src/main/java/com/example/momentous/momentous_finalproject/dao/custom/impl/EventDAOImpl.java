package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.EventDAO;
import com.example.momentous.momentous_finalproject.entity.Event;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDAOImpl implements EventDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select event_id from event order by event_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    @Override
    public ArrayList<Event> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public boolean save(Event entity) throws SQLException, ClassNotFoundException {
        boolean isEventSaved = CrudUtil.execute(
                "INSERT INTO event (event_id, booking_id, event_type, event_name, budget, venue, event_date) VALUES (?,?,?,?,?,?,?)",
                entity.getEventId(),
                entity.getBookingId(),
                entity.getEventType(),
                entity.getEventName(),
                entity.getBudget(),
                entity.getVenue(),
                entity.getDate()
        );
        return isEventSaved;
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean update(Event entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    public Event findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
