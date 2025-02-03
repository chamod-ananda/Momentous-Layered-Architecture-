package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceModel {
    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select service_id from service");

        ArrayList<String> serviceIds = new ArrayList<>();

        while (rst.next()) {
            serviceIds.add(rst.getString(1));
        }

        return serviceIds;
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from service where service_id=?", selectedServiceId);

        if (rst.next()) {
            return new ServiceDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    public String getNextServiceId() throws SQLException, ClassNotFoundException {
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

    public ArrayList<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from service");

        ArrayList<ServiceDto> serviceDtos = new ArrayList<>();

        while (rst.next()) {
            ServiceDto serviceDto = new ServiceDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3)
            );
            serviceDtos.add(serviceDto);
        }
        return serviceDtos;
    }

    public boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into service values(?,?,?)",
                serviceDto.getServiceId(),
                serviceDto.getPrice(),
                serviceDto.getServiceType()
        );
    }

    public boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update service set  price = ?, service_type = ? where service_id = ?",
                serviceDto.getPrice(),
                serviceDto.getServiceType(),
                serviceDto.getServiceId()
        );
    }

    public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from service where service_id=?", serviceId);
    }
}
