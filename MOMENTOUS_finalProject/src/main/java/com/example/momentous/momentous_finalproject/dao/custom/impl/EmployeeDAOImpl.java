package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.EmployeeDAO;
import com.example.momentous.momentous_finalproject.dto.EmployeeDto;
import com.example.momentous.momentous_finalproject.entity.Employee;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
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

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from employee");

        ArrayList<Employee> employees = new ArrayList<>();

        while (rst.next()) {
            Employee entity = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8)

            );
            employees.add(entity);
        }
        return employees;
    }

    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into employee values(?,?,?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getJobRole(),
                entity.getJoinedDate(),
                entity.getSalary(),
                entity.getEmail(),
                entity.getBookingId()
        );
    }

    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update employee set  first_name = ?,  last_name = ?, position = ?, Join_date = ?, salary = ?, email = ?, booking_id  = ? where employee_id = ?",
                entity.getFirstName(),
                entity.getLastName(),
                entity.getJobRole(),
                entity.getJoinedDate(),
                entity.getSalary(),
                entity.getEmail(),
                entity.getBookingId(),
                entity.getEmployeeId()
        );
    }

    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from employee where employee_id = ?", employeeId);
    }

    public Employee findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
