package com.bank.controller;

import com.bank.model.BankUtil;

import java.sql.SQLException;

public class BankException extends SQLException {
    public void run(){
        BankUtil.createMessage("There is an error occurred");
    }
}
