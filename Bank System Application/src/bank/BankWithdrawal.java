package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankWithdrawal {
    private BankWithdrawal() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        // the run body
        Integer withdrawalMoney = takeMoneyToWithdraw(userBankAccount);
        // if user want to exit this action
        if (withdrawalMoney.equals(-1)) return;
        withdrawMoneyFromUserBankAccount(withdrawalMoney, userBankAccount);

        // insert this log
        BankUtil.insertIntoLogTable(new Log(
                userBankAccount.getUsername(), ActivityType.WITHDRAWAL, null
        ));
        // insert this transaction
        BankUtil.insertIntoTransactionsTable(new Transaction(
                userBankAccount.getUsername(), withdrawalMoney, TransactionType.WITHDRAWAL, null
        ));
    }

    private static Integer takeMoneyToWithdraw(UserBankAccount userBankAccount) {
        String withdrawalMoney;
        do {
            System.out.print("Enter money to withdraw(-1 to exit): ");
            withdrawalMoney = BankUtil.scanner.nextLine();
            // if user want to exit this action
            if (withdrawalMoney.equals("-1")) return -1;
        } while (!withdrawalMoney.matches("[0-9]+") ||
                Integer.parseInt(withdrawalMoney) > userBankAccount.getBalance());
        return Integer.valueOf(withdrawalMoney);
    }

    private static void withdrawMoneyFromUserBankAccount(Integer withdrawalMoney, UserBankAccount userBankAccount)
            throws SQLException {
        String SQLStatement = "update userBankAccount set balance = balance - ? where id = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, Integer.toString(withdrawalMoney));
        statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
        statement.executeUpdate();
    }
}
