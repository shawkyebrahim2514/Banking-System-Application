package bank;

import bank.structure.ActivityType;
import bank.structure.Log;
import bank.structure.User;

import java.sql.SQLException;

public class BankUserProfile {
    private BankUserProfile() {
    }

    protected static void run(User user) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                user.getUsername(), ActivityType.LOGIN, null
        ));
        BankUtil.createHeader("Welcome, " + user.getFirstName() + " " + user.getLastName());
        Integer choice;
        do {
            showProfileFunctions();
            choice = takeProfileFunctionsChoice(user);
        } while (!choice.equals(5));
    }

    private static void showProfileFunctions() {
        BankUtil.createHeader("Profile functions");
        BankUtil.createOrderedList(new String[]{
                "Change my profile information",
                "View my bank accounts",
                "Open new bank account",
                "View my log",
                "Logout"
        });
    }

    private static Integer takeProfileFunctionsChoice(User user) throws SQLException {
        String choice;
        do {
            BankUtil.showTakeFunctionNumber();
            choice = BankUtil.scanner.nextLine();
        } while (!choice.matches("[1-5]"));
        runProfileFunction(user, Integer.valueOf(choice));
        return Integer.valueOf(choice);
    }

    private static void runProfileFunction(User user, Integer choice) throws SQLException {
        switch (choice) {
            case 1 -> BankChangeUserProfileInfo.run(user);
            case 2 -> BankViewAccounts.run(user);
            case 3 -> BankOpenAccount.run(user);
            case 4 -> BankLogs.run(user);
            case 5 -> logout(user);
        }
    }

    private static void logout(User user) throws SQLException {
        BankUtil.insertIntoLogTable(new Log(
                user.getUsername(), ActivityType.LOGOUT, null
        ));
    }
}
