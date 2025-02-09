package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.ServiceDAO;
import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.entity.Service;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDAOImpl implements ServiceDAO {
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select service_id from service");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }

    public Service findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from service where service_id=?", selectedServiceId);

        if (rst.next()) {
            return new Service(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT service_id FROM service ORDER BY service_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i + 1;
            return String.format("S%03d", newIndex);
        }
        return "S001";
    }

    public ArrayList<Service> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from service");

        ArrayList<Service> services = new ArrayList<>();

        while (rst.next()) {
            Service entity = new Service(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
            services.add(entity);
        }
        return services;
    }

    public boolean save(Service entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into service values(?,?,?)",
                entity.getServiceId(),
                entity.getPrice(),
                entity.getServiceType()
        );
    }

    public boolean update(Service entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update service set  price = ?, service_type = ? where service_id = ?",
                entity.getPrice(),
                entity.getServiceType(),
                entity.getServiceId()
        );
    }

    public boolean delete(String serviceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from service where service_id=?", serviceId);
    }
}
