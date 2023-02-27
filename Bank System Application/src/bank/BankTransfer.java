package bank;

import bank.structure.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransfer {
    private BankTransfer() {
    }

    protected static void run(UserBankAccount userBankAccount) throws SQLException {
        // the run body
        Integer otherUserBankAccountID = connectToAnotherUserBankAccount(userBankAccount);
        // if user want to exit this action
        if (otherUserBankAccountID.equals(-1)) return;
        transferMoney(userBankAccount, otherUserBankAccountID);

        // insert this log
        BankUtil.insertIntoLogTable(new Log(
                userBankAccount.getUsername(), ActivityType.TRANSFER, null
        ));
    }

    private static Integer connectToAnotherUserBankAccount(UserBankAccount userBankAccount) throws SQLException {
        String otherUserBankAccountID;
        do {
            System.out.print("Enter the ID of the other user bank account(-1 to exit): ");
            otherUserBankAccountID = BankUtil.scanner.nextLine();
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
        String SQLStatement = "SELECT * FROM userBankAccount WHERE id = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, otherUserBankAccountID);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            BankUtil.createMessage("This bank account ID doesn't exist!");
            return false;
        }
        if (Integer.valueOf(result.getString("id")).equals(userBankAccount.getBankAccountID())) {
            BankUtil.createMessage("You cannot transfer to the same bank account!");
            return false;
        }
        if (!Integer.valueOf(result.getString("statusID")).equals(BankAccountStatus.ACTIVE.ordinal() + 1)) {
            BankUtil.createMessage("This bank account is already closed!");
            return false;
        }
        return true;
    }

    private static void transferMoney(UserBankAccount userBankAccount, Integer otherUserBankAccountID)
            throws SQLException {
        Integer transferredMoney = takeMoneyToBeTransferred(userBankAccount);
        transferMoneyToOtherUserBankAccount(transferredMoney, otherUserBankAccountID);
        withdrawMoneyFromThisUserBankAccount(userBankAccount, transferredMoney);
        // insert this transaction
        BankUtil.insertIntoTransactionsTable(new Transaction(
                userBankAccount.getUsername(), transferredMoney, TransactionType.WITHDRAWAL, null
        ));
    }

    private static Integer takeMoneyToBeTransferred(UserBankAccount userBankAccount) {
        String moneyToBeTransferred;
        do {
            System.out.print("Enter money to be transferred: ");
            moneyToBeTransferred = BankUtil.scanner.nextLine();
        } while (!moneyToBeTransferred.matches("[0-9]+") ||
                Integer.parseInt(moneyToBeTransferred) > userBankAccount.getBalance());
        return Integer.valueOf(moneyToBeTransferred);
    }

    private static void transferMoneyToOtherUserBankAccount(Integer transferredMoney,
                                                            Integer otherUserBankAccountID) throws SQLException {
        String SQLStatement = "update userBankAccount set balance = balance + ? where id = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, Integer.toString(transferredMoney));
        statement.setString(2, Integer.toString(otherUserBankAccountID));
        statement.executeUpdate();
    }

    private static void withdrawMoneyFromThisUserBankAccount(UserBankAccount userBankAccount,
                                                             Integer transferredMoney) throws SQLException {
        String SQLStatement = "update userBankAccount set balance = balance - ? where id = ?";
        PreparedStatement statement = BankUtil.connection.prepareStatement(SQLStatement);
        statement.setString(1, Integer.toString(transferredMoney));
        statement.setString(2, Integer.toString(userBankAccount.getBankAccountID()));
        statement.executeUpdate();
    }
}