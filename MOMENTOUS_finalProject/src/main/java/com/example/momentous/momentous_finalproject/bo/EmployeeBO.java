package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO{
    String getNextEmployeeId() throws SQLException, ClassNotFoundException ;
    ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException ;
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException ;
    boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException ;
    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException ;
}
