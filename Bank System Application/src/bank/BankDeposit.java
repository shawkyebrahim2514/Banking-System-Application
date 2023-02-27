package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankDeposit {
    private BankDeposit() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        // the run body
        Integer addedMoney = takeMoneyToDeposit();
        // if user want to exit this action
        if (addedMoney.equals(-1)) return;
        depositMoneyToUserBankAccount(addedMoney, userBankAccount);

        // insert this log
        BankUtil.insertIntoLogTable(new Log(
                userBankAccount.getUsername(), ActivityType.DEPOSIT, null
        ));
        // insert this transaction
        BankUtil.insertIntoTransactionsTable(new Transaction(
                userBankAccount.getUsername(), addedMoney, TransactionType.DEPOSIT, null
        ));
    }

    private static Integer takeMoneyToDeposit() {
        String addedMoney;
        do {
            System.out.print("Enter money to deposit to your bank account(-1 to exit): ");
            addedMoney = BankUtil.scanner.nextLine();
            // if user want to exit this action
            if (addedMoney.equals("-1")) return -1;
        } while (!addedMoney.matches("[0-9]+"));
        return Integer.valueOf(addedMoney);
    }

    private static void depositMoneyToUserBankAccount(Integer addedMoney, UserBankAccount userBankAccount) throws SQLException {
        String SQLStatement = "update userBankAccount set balance = balance + ? where id = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, Integer.toString(addedMoney));
        statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
        statement.executeUpdate();
    }
}
