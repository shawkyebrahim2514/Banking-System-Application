package com.bank.model;

import com.bank.controller.BankException;
import com.bank.view.BankTransferView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransferModel {
    public static void transfer(UserBankAccount userBankAccount, Integer otherUserBankAccountID)
            throws SQLException {
        try {
            BankUtil.connection.setAutoCommit(false);
            transferMoney(userBankAccount, otherUserBankAccountID);
            // insert this log
            BankUtil.insertIntoLogTable(new Log(
                    userBankAccount.getUsername(), ActivityType.TRANSFER, null
            ));
            BankUtil.connection.commit();
        } catch (BankException e) {
            e.run();
            BankUtil.connection.rollback();
        } finally {
            BankUtil.connection.setAutoCommit(true);
        }
    }

    private static void transferMoney(UserBankAccount userBankAccount, Integer otherUserBankAccountID)
            throws SQLException {
        Integer transferredMoney = takeMoneyToBeTransferred(userBankAccount);
        transferMoneyToOtherUserBankAccount(transferredMoney, otherUserBankAccountID);
        withdrawMoneyFromThisUserBankAccount(userBankAccount, transferredMoney);
        // insert this transaction
        BankUtil.insertIntoTransactionsTable(new Transaction(
                userBankAccount.getUsername(), transferredMoney, TransactionType.WITHDRAWAL, null
        ));
    }

    private static Integer takeMoneyToBeTransferred(UserBankAccount userBankAccount) {
        String moneyToBeTransferred;
        do {
            moneyToBeTransferred = BankTransferView.takeMoney();
        } while (!moneyToBeTransferred.matches("[0-9]+") ||
                Integer.parseInt(moneyToBeTransferred) > userBankAccount.getBalance());
        return Integer.valueOf(moneyToBeTransferred);
    }

    private static void transferMoneyToOtherUserBankAccount(Integer transferredMoney,
                                                            Integer otherUserBankAccountID) throws SQLException {
        String SQLStatement = "call updateBankAccountBalance(?,?)";
        updateUserBankAccountTable(SQLStatement, transferredMoney, otherUserBankAccountID);
    }

    private static void withdrawMoneyFromThisUserBankAccount(UserBankAccount userBankAccount,
                                                             Integer transferredMoney) throws SQLException {
        String SQLStatement = "call updateBankAccountBalance(-?,?)";
        updateUserBankAccountTable(SQLStatement, transferredMoney, userBankAccount.getBankAccountID());
    }

    private static void updateUserBankAccountTable(String SQLStatement, Integer transferredMoney,
                                                   Integer bankAccountID) throws SQLException {
        try {
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setInt(1, transferredMoney);
            statement.setInt(2, bankAccountID);
            statement.addBatch();
            statement.executeBatch();
            statement.close();
        } catch (BankException e) {
            e.run();
        }
    }

    public static boolean checkValidAccount(UserBankAccount userBankAccount, String otherUserBankAccountID)
            throws SQLException {
        Boolean isValidBankAccount = null;
        try {
            String SQLStatement = "call checkValidBankAccount(?)";
            PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
            statement.setString(1, otherUserBankAccountID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            isValidBankAccount = resultSet.getBoolean("isValidBankAccount");
            if (!isValidBankAccount) {
                BankTransferView.showInvalidAccount();
            }
            statement.close();
            resultSet.close();
        } catch (BankException e) {
            e.run();
        }
        return isValidBankAccount;
    }
}
