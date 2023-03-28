package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankModifyProfileModel {
    public static void updatePassword(String newValue, String username) throws SQLException {
        try {
            String SQLStatement = "call updateUserPassword(?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, newValue);
            statement.setString(2, username);
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
    public static void updateProfileInfo(String columnToSet, String newValue, String username) throws SQLException {
        try {
            String SQLStatement = "call updateUserInfo(?,?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, columnToSet);
            statement.setString(2, newValue);
            statement.setString(3, username);
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}
