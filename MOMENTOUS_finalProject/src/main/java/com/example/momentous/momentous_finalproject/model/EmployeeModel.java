package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.EmployeeDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i + 1;
            return String.format("M%03d", newIndex);
        }
        return "M001";
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto dto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8)

            );
            employeeDtos.add(dto);
        }
        return employeeDtos;
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from employee where employee_id = ?", employeeId);
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into employee values(?,?,?,?,?,?,?,?)",
                dto.getEmployeeId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getJobRole(),
                dto.getJoinedDate(),
                dto.getSalary(),
                dto.getEmail(),
                dto.getBookingId()
        );
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update employee set  first_name = ?,  last_name = ?, position = ?, Join_date = ?, salary = ?, email = ?, booking_id  = ? where employee_id = ?",
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getJobRole(),
                employeeDto.getJoinedDate(),
                employeeDto.getSalary(),
                employeeDto.getEmail(),
                employeeDto.getBookingId(),
                employeeDto.getEmployeeId()
        );
    }
}



