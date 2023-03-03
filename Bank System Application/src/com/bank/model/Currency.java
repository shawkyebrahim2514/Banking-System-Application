package com.bank.model;

public enum Currency {
    DOLLAR("Dollar", '$'),
    EURO("Euro", '€'),
    JapaneseYen("Japanese yen", '¥'),
    GreatBritishPound("Great British Pound", '£');

    public static void showAll() {
        BankUtil.createMessage("There are 4 types of currencies");
        System.out.println(Currency.DOLLAR);
        System.out.println(Currency.EURO);
        System.out.println(Currency.JapaneseYen);
        System.out.println(Currency.GreatBritishPound);
    }

    public static Currency typeInIndex(Integer index) {
        return Currency.values()[index];
    }

    private final Character symbol;

    private final String name;

    Currency(String name, Character symbol) {
        this.symbol = symbol;
        this.name = name;
    }

    public Character getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return (ordinal() + 1) + "- " + name + " (" + symbol + ")";
    }
}
