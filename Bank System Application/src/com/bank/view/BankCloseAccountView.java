package com.bank.view;

import com.bank.model.BankUtil;

public class BankCloseAccountView {
    public static void showSuccessfulClosing() {
        BankUtil.createMessage("Successfully closed!");
    }
}
