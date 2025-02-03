package com.example.momentous.momentous_finalproject.dao.custom;

import com.example.momentous.momentous_finalproject.dao.CrudDAO;
import com.example.momentous.momentous_finalproject.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean isEmailExists(String text) throws SQLException, ClassNotFoundException ;
}
