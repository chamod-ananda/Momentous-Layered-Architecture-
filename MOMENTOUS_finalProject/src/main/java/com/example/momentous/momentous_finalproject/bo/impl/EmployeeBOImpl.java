package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.EmployeeBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.EmployeeDAOImpl;
import com.example.momentous.momentous_finalproject.dto.EmployeeDto;
import com.example.momentous.momentous_finalproject.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAOImpl employeeDAO = (EmployeeDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.EMPLOYEE);

    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        ArrayList<Employee> employeeList = employeeDAO.getAll();
        for (Employee employee : employeeList) {
            employeeDtos.add(
                    new EmployeeDto(
                            employee.getEmployeeId(),
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getJobRole(),
                            employee.getJoinedDate(),
                            employee.getSalary(),
                            employee.getEmail(),
                            employee.getBookingId()
                    ));



        }
        return employeeDtos;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(
                new Employee(
                        dto.getEmployeeId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getJobRole(),
                        dto.getJoinedDate(),
                        dto.getSalary(),
                        dto.getEmail(),
                        dto.getBookingId()
                ));

    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(
                new Employee(
                        employeeDto.getEmployeeId(),
                        employeeDto.getFirstName(),
                        employeeDto.getLastName(),
                        employeeDto.getJobRole(),
                        employeeDto.getJoinedDate(),
                        employeeDto.getSalary(),
                        employeeDto.getEmail(),
                        employeeDto.getBookingId()
                ));
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }
}
