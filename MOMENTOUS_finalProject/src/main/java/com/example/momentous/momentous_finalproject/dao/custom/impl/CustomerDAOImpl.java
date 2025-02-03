package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.CustomerDAO;
import com.example.momentous.momentous_finalproject.entity.Customer;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;
            return String.format("C%03d", newIndex);
        }
        return "C001";
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<Customer> customers = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getDate(7)
            );
            customers.add(customer);
        }
        return customers;
    }

    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into customer values(?,?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getRegDate()
        );
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from customer where customer_id = ?", customerId);
    }

    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update customer set cust_title = ?, first_name = ?, last_name = ?, nic = ?, email = ?, registration_date = ? where customer_id = ?",
                entity.getCustomerTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getRegDate(),
                entity.getCustomerId()
        );
    }

    public Customer findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id = ?", selectedCustomerId);

        if (rst.next()) {
            return new Customer(
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
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }
}
