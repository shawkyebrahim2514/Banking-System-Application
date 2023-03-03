package com.bank.view;

import com.bank.model.BankUtil;
import com.bank.model.Transaction;

import java.util.List;

public class BankTransactionsView {
    public static void displayTransactions(List<Transaction> transactions) {
        BankUtil.createHeader("My transactions");
        for (int i = 0; i < transactions.size(); ++i) {
            BankUtil.createMessage("Account number (" + (i + 1) + ")");
            System.out.println(transactions.get(i));
        }
    }

    public static String askMoreTransactions(){
        BankUtil.createMessage("Want more transactions? y(yes), n(no)");
        return BankUtil.scanner.nextLine();
    }
}
