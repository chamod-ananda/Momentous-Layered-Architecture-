package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.UserBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.UserDAOImpl;
import com.example.momentous.momentous_finalproject.dto.UserDto;
import com.example.momentous.momentous_finalproject.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAOImpl userDAO = (UserDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.USER);

    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        return userDAO.getNextId();
    }

    @Override
    public boolean saveUser(UserDto user) throws SQLException, ClassNotFoundException {
        return userDAO.save(
                new User(user.getUserId(),user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmail(),user.getPassword()
                ));
    }

    @Override
    public boolean deleteUser(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public boolean isEmailExists(String text) throws SQLException, ClassNotFoundException {
        return userDAO.isEmailExists(text);
    }

    @Override
    public boolean updateUser(UserDto user) throws SQLException, ClassNotFoundException {
        return userDAO.update(
                new User(user.getUserId(),user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmail(),user.getPassword()
                ));
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDto> userDtos = new ArrayList<>();
        ArrayList<User> usersList = userDAO.getAll();
        for (User user : usersList) {
            userDtos.add(
                    new UserDto
                            (user.getUserId(),user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmail(),user.getPassword()
                            ));

        }
        return userDtos;
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException {
        return userDAO.getAllIds();
    }

    @Override
    public UserDto findById(String selectedUserId) throws SQLException, ClassNotFoundException {
        User user = userDAO.findById(selectedUserId);
        return new UserDto(
                user.getUserId(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(), user.getPassword()
        );

    }
}
