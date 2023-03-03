package com.bank.controller;

import com.bank.model.ActivityType;
import com.bank.model.BankLogsModel;
import com.bank.model.Log;
import com.bank.model.User;
import com.bank.view.BankLogsView;

import java.sql.SQLException;
import java.util.List;

public class BankLogs {
    private static final Integer limitNumber = 5;

    private BankLogs() {
    }

    protected static void run(User user) throws SQLException {
        boolean wantMore;
        Integer numberOfLogs = BankLogsModel.getNumberOfLogs(user);
        int offsetNumber = 0;
        do {
            List<Log> logs = BankLogsModel.collectLogs(user.getUsername(), limitNumber, offsetNumber);
            BankLogsView.showLogs(logs);
            offsetNumber += 5;
            wantMore = checkWantMoreLogs();
        } while (wantMore && offsetNumber <= numberOfLogs);
    }

    public static ActivityType getActivityType(Integer activityID) {
        ActivityType[] activityTypes = ActivityType.values();
        return activityTypes[activityID - 1];
    }

    private static boolean checkWantMoreLogs() {
        String respond;
        do {
            respond = BankLogsView.askMoreLogs();
        } while (!respond.equals("y") && !respond.equals("n"));
        return respond.equals("y");
    }
}
