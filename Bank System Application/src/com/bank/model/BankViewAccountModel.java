package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankViewAccountModel {

    public static List<UserBankAccount> collectUserBankAccounts(User user) throws SQLException {
        List<UserBankAccount> bankAccounts = new LinkedList<>();
        try {
            String SQLStatement = "call getUserBankAccounts(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer bankAccountID = resultSet.getInt("id");
                Integer typeID = resultSet.getInt("typeID");
                Integer currencyID = resultSet.getInt("currencyID");
                Integer statusID = resultSet.getInt("statusID");
                Integer balance = resultSet.getInt("balance");
                Integer withdrawalLimit = resultSet.getInt("withdrawalLimit");
                String date = resultSet.getString("createdAt");
                UserBankAccount bankAccount = new UserBankAccount(user.getUsername(), bankAccountID,
                        getBankAccountType(typeID), getCurrency(currencyID), getStatus(statusID), balance,
                        withdrawalLimit, date);
                bankAccounts.add(bankAccount);
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return bankAccounts;
    }

    private static BankAccountType getBankAccountType(Integer typeID) {
        switch (typeID) {
            case 1 -> {
                return BankAccountType.BASIC;
            }
            case 2 -> {
                return BankAccountType.SAVING;
            }
        }
        return null;
    }

    private static Currency getCurrency(Integer currencyID) {
        switch (currencyID) {
            case 1 -> {
                return Currency.DOLLAR;
            }
            case 2 -> {
                return Currency.EURO;
            }
            case 3 -> {
                return Currency.JapaneseYen;
            }
            case 4 -> {
                return Currency.GreatBritishPound;
            }
        }
        return null;
    }

    private static BankAccountStatus getStatus(Integer statusID) {
        switch (statusID) {
            case 1 -> {
                return BankAccountStatus.ACTIVE;
            }
            case 2 -> {
                return BankAccountStatus.CLOSED;
            }
            case 3 -> {
                return BankAccountStatus.PENDING;
            }
        }
        return null;
    }

    public static void insertLog(String username) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                username, ActivityType.ViewBankAccounts, null
        ));
    }
}
