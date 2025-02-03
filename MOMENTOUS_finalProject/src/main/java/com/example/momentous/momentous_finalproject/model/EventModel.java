package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.EventDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventModel {
    private final EventSupplierModel eventSupplierModel = new EventSupplierModel();

    public String getNextEventId() throws SQLException {
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

    public boolean saveEvent(EventDto eventDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isEventSaved = CrudUtil.execute(
                    "insert into event values (?,?,?,?,?,?,?)",
                    eventDto.getEventId(),
                    eventDto.getBookingId(),
                    eventDto.getEventType(),
                    eventDto.getEventName(),
                    eventDto.getBudget(),
                    eventDto.getVenue(),
                    eventDto.getDate()
            );

            if (isEventSaved) {
                boolean isEventDetailListSaved = eventSupplierModel.saveEventSuppliersList(eventDto.getEventSupplierDtos());

                if (isEventDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
