package com.bank.model;

public enum BankAccountType {
    BASIC(
            "Basic", 0, 2, 4, 1000, 50
    ),
    SAVING(
            "Saving", 500, 3, 5, 750, 150
    );

    public static void showAll() {
        BankUtil.createMessage("There are 2 types of bank accounts");
        System.out.println(BankAccountType.BASIC);
        System.out.println();
        System.out.println(BankAccountType.SAVING);
    }

    public static BankAccountType typeInIndex(Integer index) {
        return BankAccountType.values()[index];
    }

    public final String name;
    public final Integer minimumBalanceInAccount;
    public final Integer fees;
    public final Integer interest;
    public final Integer minimumBalanceToInterest;
    public final Integer withdrawalLimits;

    BankAccountType(String name, Integer minimumBalanceInAccount, Integer fees, Integer interest,
                    Integer minimumBalanceToInterest, Integer withdrawalLimits) {
        this.name = name;
        this.minimumBalanceInAccount = minimumBalanceInAccount;
        this.fees = fees;
        this.interest = interest;
        this.minimumBalanceToInterest = minimumBalanceToInterest;
        this.withdrawalLimits = withdrawalLimits;
    }

    @Override
    public String toString() {
        return "The " + name + " bank account (" + (ordinal() + 1) + ")\n" +
                "The minimum balance can have = " + minimumBalanceInAccount + "\n" +
                "Its fees = " + fees + "\n" +
                "Its interest = " + interest + "\n" +
                "The minimum balance to get the interest = " + minimumBalanceToInterest + "\n" +
                "The withdrawal limit per month = " + withdrawalLimits + "\n";
    }

    public boolean checkValidBalance(Integer balance) {
        return balance >= this.minimumBalanceInAccount;
    }
}
