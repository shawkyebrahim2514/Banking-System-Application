package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankOpenAccount {
    private BankOpenAccount() {
    }

    protected static void run(User user) throws SQLException {
        UserBankAccount newAccount;
        int typeID, currencyID, statusID, balance;
        BankAccountType bankAccountType;
        Currency currencyType;
        BankAccountStatus bankAccountStatusType;
        typeID = takeAccountType();
        bankAccountType = BankAccountType.typeInIndex(typeID - 1);
        currencyID = takeAccountCurrency();
        currencyType = Currency.typeInIndex(currencyID - 1);
        statusID = BankAccountStatus.PENDING.ordinal() + 1;
        bankAccountStatusType = BankAccountStatus.typeInIndex(statusID - 1);
        balance = takeBalance(bankAccountType);
        newAccount = new UserBankAccount(user.getUsername(), null, bankAccountType, currencyType,
                bankAccountStatusType, balance, null);
        saveNewAccount(user, newAccount);
    }

    private static Integer takeAccountType() {
        BankAccountType.showAll();
        String typeID;
        do {
            System.out.print("Enter the bank type number: ");
            typeID = BankUtil.scanner.nextLine();
        } while (!typeID.matches("[1-2]"));
        return Integer.valueOf(typeID);
    }

    private static Integer takeAccountCurrency() {
        Currency.showAll();
        String currencyID;
        do {
            System.out.print("Enter the currency number: ");
            currencyID = BankUtil.scanner.nextLine();
        } while (!currencyID.matches("[1-4]"));
        return Integer.valueOf(currencyID);
    }

    private static Integer takeBalance(BankAccountType bankAccountType) {
        String balance;
        do {
            System.out.print("Enter the initial balance for this bank account: ");
            balance = BankUtil.scanner.nextLine();
        } while (!balance.matches("[0-9]+") || !bankAccountType.checkValidBalance(Integer.valueOf(balance)));
        return Integer.valueOf(balance);
    }

    private static void saveNewAccount(User user, UserBankAccount newAccount) throws SQLException {
        String SQLStatement = "insert into userBankAccount (username, typeID, currencyID, statusID, balance) " +
                "values (?, ?, ?, ?, ?)";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, user.getUsername());
        statement.setString(2, Integer.toString(newAccount.getType().ordinal() + 1));
        statement.setString(3, Integer.toString(newAccount.getCurrency().ordinal() + 1));
        statement.setString(4, Integer.toString(newAccount.getStatus().ordinal() + 1));
        statement.setString(5, Integer.toString(newAccount.getBalance()));
        statement.executeUpdate();
    }
}
