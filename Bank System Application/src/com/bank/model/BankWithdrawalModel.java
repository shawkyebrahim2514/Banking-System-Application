package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankWithdrawalModel {
    public static void withdraw(Integer withdrawalMoney, UserBankAccount userBankAccount) throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            withdrawMoneyFromUserBankAccount(withdrawalMoney, userBankAccount);
            // insert this log
            BankUtil.insertIntoLogTable(new Log(
                    userBankAccount.getUsername(), ActivityType.WITHDRAWAL, null
            ));
            // insert this transaction
            BankUtil.insertIntoTransactionsTable(new Transaction(
                    userBankAccount.getUsername(), withdrawalMoney, TransactionType.WITHDRAWAL, null
            ));
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void withdrawMoneyFromUserBankAccount(Integer withdrawalMoney, UserBankAccount userBankAccount)
            throws SQLException {
        try {
            String SQLStatement = "call updateBankAccountBalance(-?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, Integer.toString(withdrawalMoney));
            statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}
