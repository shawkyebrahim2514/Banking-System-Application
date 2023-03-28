package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankRegistrationModel {
    public static Boolean checkUniqueUsername(String username) throws SQLException {
        Boolean isUniqueUsername = null;
        try {
            String SQLStatement = "call getUserPassword(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            isUniqueUsername = !statement.executeQuery().next();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
        return isUniqueUsername;
    }

    public static Boolean checkUniqueEmail(String email) throws SQLException {
        Boolean isUniqueEmail = null;
        try {
            String SQLStatement = "call checkUniqueEmail(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isUniqueEmail = resultSet.getBoolean("isUniqueEmail");
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return isUniqueEmail;
    }

    public static void saveNewUser(User user) throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            insertIntoUsersTable(user);
            insertIntoUsersInfoTable(user);
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void insertIntoUsersTable(User user) throws SQLException {
        try {
            String SQLStatement = "call insertUser(?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    private static void insertIntoUsersInfoTable(User user) throws SQLException {
        try {
            String SQLStatement = "call insertUserInfo(?,?,?,?,?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getEmail());
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}
