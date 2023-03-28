package com.bank.model;

import com.bank.controller.BankException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankDepositModel {
    public static void deposit(Integer addedMoney, UserBankAccount userBankAccount) throws SQLException{
        try {
            BankUtil.connection.setAutoCommit(false);
            depositMoneyToUserBankAccount(addedMoney, userBankAccount);
            // insert this log
            BankUtil.insertIntoLogTable(new Log(
                    userBankAccount.getUsername(), ActivityType.DEPOSIT, null
            ));
            // insert this transaction
            BankUtil.insertIntoTransactionsTable(new Transaction(
                    userBankAccount.getUsername(), addedMoney, TransactionType.DEPOSIT, null
            ));
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }

    }

    public static void depositMoneyToUserBankAccount(Integer addedMoney, UserBankAccount userBankAccount)
            throws SQLException {
        try {
            String SQLStatement = "call updateBankAccountBalance(?,?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, Integer.toString(addedMoney));
            statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }
}
