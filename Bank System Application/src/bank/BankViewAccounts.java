package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BankViewAccounts {
    private BankViewAccounts() {
    }

    protected static void run(User user) throws SQLException {
        // insert this log
        BankUtil.insertIntoLogTable(new Log(
                user.getUsername(), ActivityType.ViewBankAccounts, null
        ));
        // the run body
        int functionNumber, userBankAccountNumber;
        List<UserBankAccount> userBankAccounts = showUserBankAccounts(user);
        boolean hasActiveAccounts = false;
        for (UserBankAccount userBankAccount : userBankAccounts) {
            if (userBankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
                hasActiveAccounts = true;
            }
        }
        if (!hasActiveAccounts) {
            BankUtil.createMessage("All your bank accounts are closed, you cannot do anything here!");
            return;
        }
        do {
            showUserBankAccountFunctions();
            functionNumber = takeUserBankAccountFunction();
            if (functionNumber == 6) break;
            userBankAccountNumber = takeUserBankAccountNumber(userBankAccounts);
            runUserBankAccountFunction(functionNumber, userBankAccounts.get(userBankAccountNumber - 1));
        } while (true);
    }

    private static List<UserBankAccount> showUserBankAccounts(User user) throws SQLException {
        List<UserBankAccount> userBankAccounts = collectUserBankAccounts(user);
        int accountNumber = 1;
        BankUtil.createHeader("My Bank Accounts");
        for (UserBankAccount account : userBankAccounts) {
            BankUtil.createMessage("Account number (" + accountNumber + ")");
            System.out.println(account);
            accountNumber++;
        }
        return userBankAccounts;
    }

    private static void showUserBankAccountFunctions() {
        BankUtil.createHeader("What do you want to do?");
        BankUtil.createOrderedList(new String[]{
                "Deposit",
                "Withdraw",
                "Transfer",
                "Show transactions",
                "Close bank account",
                "Exit"
        });
    }

    private static Integer takeUserBankAccountFunction() {
        String choice;
        do {
            BankUtil.showTakeFunctionNumber();
            choice = BankUtil.scanner.nextLine();
        } while (!choice.matches("[1-6]"));
        return Integer.valueOf(choice);
    }

    private static Integer takeUserBankAccountNumber(List<UserBankAccount> userBankAccounts) {
        String userBankAccountNumber;
        do {
            System.out.print("Enter bank account number from the list to do this function on: ");
            userBankAccountNumber = BankUtil.scanner.nextLine();
        } while (!userBankAccountNumber.matches("[1-" + userBankAccounts.size() + "]") ||
                !checkBankAccountActive(userBankAccounts.get(Integer.parseInt(userBankAccountNumber) - 1)));
        return Integer.valueOf(userBankAccountNumber);
    }

    private static boolean checkBankAccountActive(UserBankAccount userBankAccount) {
        if (userBankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
            return true;
        } else {
            BankUtil.createMessage("This bank account isn't active!");
            return false;
        }
    }

    private static void runUserBankAccountFunction(Integer choice, UserBankAccount userBankAccount) throws SQLException {
        switch (choice) {
            case 1 -> BankDeposit.run(userBankAccount);
            case 2 -> BankWithdrawal.run(userBankAccount);
            case 3 -> BankTransfer.run(userBankAccount);
            case 4 -> BankTransactions.run(userBankAccount);
            case 5 -> BankCloseAccount.run(userBankAccount);
        }
    }

    private static List<UserBankAccount> collectUserBankAccounts(User user) throws SQLException {
        List<UserBankAccount> bankAccounts = new LinkedList<>();
        String SQLStatement = "SELECT * FROM userBankAccount WHERE username = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, user.getUsername());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Integer bankAccountID = result.getInt("id");
            Integer typeID = result.getInt("typeID");
            Integer currencyID = result.getInt("currencyID");
            Integer statusID = result.getInt("statusID");
            Integer balance = result.getInt("balance");
            String date = result.getString("createdAt");
            UserBankAccount bankAccount = new UserBankAccount(user.getUsername(), bankAccountID,
                    getBankAccountType(typeID), getCurrency(currencyID), getStatus(statusID), balance, date);
            bankAccounts.add(bankAccount);
        }
        return bankAccounts;
    }

    private static BankAccountType getBankAccountType(Integer typeID) {
        switch (typeID) {
            case 1 -> {
                return BankAccountType.BASIC;
            }
            case 2 -> {
                return BankAccountType.SAVING;
            }
        }
        return null;
    }

    private static Currency getCurrency(Integer currencyID) {
        switch (currencyID) {
            case 1 -> {
                return Currency.DOLLAR;
            }
            case 2 -> {
                return Currency.EURO;
            }
            case 3 -> {
                return Currency.JapaneseYen;
            }
            case 4 -> {
                return Currency.GreatBritishPound;
            }
        }
        return null;
    }

    private static BankAccountStatus getStatus(Integer statusID) {
        switch (statusID) {
            case 1 -> {
                return BankAccountStatus.ACTIVE;
            }
            case 2 -> {
                return BankAccountStatus.CLOSED;
            }
            case 3 -> {
                return BankAccountStatus.PENDING;
            }
        }
        return null;
    }
}