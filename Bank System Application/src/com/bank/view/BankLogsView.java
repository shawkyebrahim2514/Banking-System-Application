package com.bank.view;

import com.bank.model.BankUtil;
import com.bank.model.Log;

import java.util.List;

public class BankLogsView {
    public static void showLogs(List<Log> logs) {
        for (int i = 0; i < logs.size(); ++i) {
            BankUtil.createMessage("Log number (" + (i + 1) + ")");
            System.out.println(logs.get(i));
        }
    }

    public static String askMoreLogs() {
        BankUtil.createMessage("Want more logs? y(yes), n(no)");
        return BankUtil.scanner.nextLine();
    }
}
