package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO{
    String getNextCustomerId() throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException ;
    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException ;
    boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException ;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException ;
    CustomerDto findById(String selectedCustomerId) throws SQLException, ClassNotFoundException ;
    ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException ;
}
