package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankTransactionsModel {
    public static List<Transaction> collectTransactions(String username, Integer limitNumber,
                                                        Integer offsetNumber) throws SQLException {
        List<Transaction> transactions = new LinkedList<>();
        try {
            String SQLStatement = "call getUserTransactions(?,?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            statement.setInt(2, limitNumber);
            statement.setInt(3, offsetNumber);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Integer amount = result.getInt("amount");
                Integer typeID = result.getInt("typeID");
                String date = result.getString("createdAt");
                transactions.add(new Transaction(
                        username, amount, getTransactionType(typeID), date
                ));
            }
        } catch (BankException e) {
            e.run();
        }
        return transactions;
    }

    private static TransactionType getTransactionType(Integer typeID) {
        TransactionType[] transactionTypes = TransactionType.values();
        return transactionTypes[typeID - 1];
    }

    public static void insertLog(String username) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                username, ActivityType.ViewTransactionHistory, null
        ));
    }

    public static Integer getNumberOfTransactions(String username) throws SQLException {
        Integer numberOfTransactions = null;
        try {
            String SQLStatement = "call getNumberOfUserTransactions(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            numberOfTransactions = resultSet.getInt("numberOfTransactions");
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return numberOfTransactions;
    }
}
