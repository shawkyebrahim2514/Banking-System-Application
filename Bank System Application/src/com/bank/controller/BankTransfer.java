package com.bank.controller;

import com.bank.model.BankTransferModel;
import com.bank.model.UserBankAccount;
import com.bank.view.BankTransferView;
import com.bank.view.BankWithdrawalView;

import java.sql.SQLException;

public class BankTransfer {
    private BankTransfer() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        if (userBankAccount.getWithdrawalLimit() == 0) {
            BankWithdrawalView.showWithdrawalEnd();
            return;
        }
        Integer otherUserBankAccountID = connectToAnotherUserBankAccount(userBankAccount);
        // if user want to exit this action
        if (otherUserBankAccountID.equals(-1)) return;
        BankTransferModel.transfer(userBankAccount, otherUserBankAccountID);
        ;
    }

    private static Integer connectToAnotherUserBankAccount(UserBankAccount userBankAccount) throws SQLException {
        String otherUserBankAccountID;
        do {
            otherUserBankAccountID = BankTransferView.takeOtherAccount();
            // if user want to exit this action
            if (otherUserBankAccountID.equals("-1")) return -1;
        } while (!checkValidOtherUserBankAccountID(userBankAccount, otherUserBankAccountID));
        return Integer.valueOf(otherUserBankAccountID);
    }

    private static boolean checkValidOtherUserBankAccountID(UserBankAccount userBankAccount,
                                                            String otherUserBankAccountID) throws SQLException {
        if (!otherUserBankAccountID.matches("[0-9]+")) {
            return false;
        }
        if (Integer.valueOf(otherUserBankAccountID).equals(userBankAccount.getBankAccountID())) {
            BankTransferView.showSameAccount();
            return false;
        }
        return BankTransferModel.checkValidAccount(userBankAccount, otherUserBankAccountID);
    }
}