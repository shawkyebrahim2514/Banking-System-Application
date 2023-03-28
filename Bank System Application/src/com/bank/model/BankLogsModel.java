package com.bank.model;

import com.bank.controller.BankException;
import com.bank.controller.BankLogs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankLogsModel {

    public static Integer getNumberOfLogs(User user) throws SQLException {
        Integer numberOfLogs = null;
        try {
            String SQLStatement = "call getNumberOfUserLogs(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            numberOfLogs = resultSet.getInt("numberOfLogs");
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return numberOfLogs;
    }

    public static List<Log> collectLogs(String username, Integer limitNumber, Integer offsetNumber)
            throws SQLException {
        List<Log> logs = new LinkedList<>();
        try {
            String SQLStatement = "call getUserLogs(?,?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            statement.setInt(2, limitNumber);
            statement.setInt(3, offsetNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer activityID = resultSet.getInt("activityID");
                String createdAt = resultSet.getString("createdAt");
                Log log = new Log(username, BankLogs.getActivityType(activityID), createdAt);
                logs.add(log);
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return logs;
    }
}
