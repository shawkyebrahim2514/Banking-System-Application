package com.bank.controller;

import com.bank.model.BankTransactionsModel;
import com.bank.model.Transaction;
import com.bank.model.UserBankAccount;
import com.bank.view.BankTransactionsView;

import java.sql.SQLException;
import java.util.List;

public class BankTransactions {
    private static final Integer limitNumber = 5;

    private BankTransactions() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        BankTransactionsModel.insertLog(userBankAccount.getUsername());
        boolean wantMore;
        Integer numberOfLogs = BankTransactionsModel.getNumberOfTransactions(userBankAccount.getUsername());
        int offsetNumber = 0;
        do {
            List<Transaction> transactions = BankTransactionsModel.collectTransactions(userBankAccount.getUsername(),
                    limitNumber, offsetNumber);
            BankTransactionsView.displayTransactions(transactions);
            offsetNumber += 5;
            wantMore = checkWantMoreLogs();
        } while (wantMore && offsetNumber <= numberOfLogs);
    }

    private static boolean checkWantMoreLogs() {
        String respond;
        do {
            respond = BankTransactionsView.askMoreTransactions();
        } while (!respond.equals("y") && !respond.equals("n"));
        return respond.equals("y");
    }
}
