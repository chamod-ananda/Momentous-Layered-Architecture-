package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.dto.CustomerDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    /*public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;
            return String.format("C%03d", newIndex);
        }
        return "C001";
    }*/

    /*public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDtos = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }*/

    /*public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into customer values(?,?,?,?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getCustomerTitle(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getRegDate()
        );
    }*/

    /*public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id = ?", customerId);
    }*/

    /*public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update customer set cust_title = ?, first_name = ?, last_name = ?, nic = ?, email = ?, registration_date = ? where customer_id = ?",
                customerDto.getCustomerTitle(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getRegDate(),
                customerDto.getCustomerId()
        );
    }*/

    /*public CustomerDto findByCustomerId(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id = ?", selectedCustomerId);

        if (rst.next()) {
            return new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
        }
        return null;
    }*/

    /*public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }*/
}
