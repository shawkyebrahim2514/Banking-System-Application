package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankLoginModel {
    public static String getPasswordOf(String username) throws SQLException {
        String password = null;
        try {
            String SQLStatement = "call getUserPassword(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return password;
    }

    public static User collectUserData(String username, String password) throws SQLException {
        User user = null;
        try {
            String SQLStatement = "call getUserInfo(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(username, password, resultSet.getString("firstName"),
                        resultSet.getString("lastName"), resultSet.getString("phoneNumber"),
                        resultSet.getString("address"), resultSet.getString("email"));
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return user;
    }
}
