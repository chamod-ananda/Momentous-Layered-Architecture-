package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.ServiceDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceBO extends SuperBO{
    ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException ;
    ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException ;
    String getNextServiceId() throws SQLException, ClassNotFoundException ;
    ArrayList<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException ;
    boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException ;
    boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException ;
    boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException ;
}
