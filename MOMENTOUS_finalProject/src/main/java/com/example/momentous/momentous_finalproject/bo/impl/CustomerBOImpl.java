package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.CustomerBO;
import com.example.momentous.momentous_finalproject.dao.custom.impl.CustomerDAOImpl;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dto.CustomerDto;
import com.example.momentous.momentous_finalproject.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.CUSTOMER);
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        ArrayList<Customer> customerList = customerDAO.getAll();
        for (Customer customer : customerList) {
            customerDtos.add(
                    new CustomerDto
                            (customer.getCustomerId(), customer.getCustomerTitle(), customer.getFirstName(), customer.getLastName(), customer.getNic(), customer.getEmail(), customer.getRegDate()
                            ));

        }
        return customerDtos;
    }

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(
                new Customer
                        (customerDto.getCustomerId(),customerDto.getCustomerTitle(),customerDto.getFirstName(),customerDto.getLastName(),customerDto.getNic(),customerDto.getEmail(),customerDto.getRegDate()
                        ));
    }

    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(
                new Customer
                        (customerDto.getCustomerId(),customerDto.getCustomerTitle(),customerDto.getFirstName(),customerDto.getLastName(),customerDto.getNic(),customerDto.getEmail(),customerDto.getRegDate()
                        ));
    }

    public CustomerDto findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.findById(selectedCustomerId);
        return new CustomerDto
                (customer.getCustomerId(),customer.getCustomerTitle(),customer.getFirstName(),customer.getLastName(),customer.getNic(),customer.getEmail(),customer.getRegDate()
                );
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllIds();
    }
}
