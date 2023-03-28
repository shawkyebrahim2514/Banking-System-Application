package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankCloseAccountModel {
    public static void closeAccount(UserBankAccount userBankAccount) throws SQLException {
        try {
            String SQLStatement = "call updateBankAccountStatus(?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, Integer.toString(BankAccountStatus.CLOSED.ordinal() + 1));
            statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
            statement.executeUpdate();
        } catch (BankException e) {
            e.run();
        }
    }
}
