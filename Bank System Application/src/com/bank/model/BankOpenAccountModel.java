package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankOpenAccountModel {
    public static void saveNewAccount(User user, UserBankAccount newAccount) throws SQLException {
        try {
            String SQLStatement = "call insertBankAccount(?,?,?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, user.getUsername());
            statement.setString(2, Integer.toString(newAccount.getType().ordinal() + 1));
            statement.setString(3, Integer.toString(newAccount.getCurrency().ordinal() + 1));
            statement.setString(4, Integer.toString(newAccount.getBalance()));
            statement.executeUpdate();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}
