package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.UserDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public boolean saveUser(UserDto userDto) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into user values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, userDto.getUserId());
        preparedStatement.setString(2, userDto.getFirstName());
        preparedStatement.setString(3, userDto.getLastName());
        preparedStatement.setString(4, userDto.getUserName());
        preparedStatement.setString(5, userDto.getEmail());
        preparedStatement.setString(6, userDto.getPassword());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateUser(UserDto userDto) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update user set first_name = ?, last_name = ?, username = ?, email = ?, password = ? where user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userDto.getFirstName());
        preparedStatement.setString(2, userDto.getLastName());
        preparedStatement.setString(3, userDto.getUserName());
        preparedStatement.setString(4, userDto.getEmail());
        preparedStatement.setString(5, userDto.getPassword());
        preparedStatement.setString(6, userDto.getUserId());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean isEmailExists(String email) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select email from user where email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public String getNextUserId() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select user_id from user order by user_id desc limit 1";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            String string = resultSet.getString(1);
            String substring = string.substring(1);
            int lastIndex = Integer.parseInt(substring);
            int nextIndex = lastIndex + 1;

            String nextId = String.format("U%03d", nextIndex);
            return nextId;
        }
        return "U001";
    }

    public ArrayList<UserDto> getAllUsers() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from user";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<UserDto> userDtos = new ArrayList<>();

        while (resultSet.next()){
            UserDto userDto = new UserDto(
                resultSet.getString("user_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute("delete from user where user_id = ?", userId);
    }

    public ArrayList<String> getAllUserIds() throws SQLException{
        ResultSet rst = CrudUtil.execute("select user_id from user");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }
        return userIds;
    }

    public UserDto findBy(String selecctedUserId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user where user_id = ?", selecctedUserId);

        if (rst.next()) {
            return new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
