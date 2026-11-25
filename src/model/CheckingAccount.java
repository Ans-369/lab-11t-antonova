package model;

import exceptions.AccountException;

public class CheckingAccount extends BankAccount {
    private double dailyWithdrawalLimit = 300.0;

    public CheckingAccount(String userId, String customerName, String password, double balance) {
        super(userId, customerName, password, balance);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit < 0) {
            throw new AccountException("Daily withdrawal limit cannot be negative");
        }
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > dailyWithdrawalLimit) {
            throw new AccountException("Amount exceeds daily withdrawal limit (" + dailyWithdrawalLimit + ")");
        }
        super.withdraw(amount);
    }
}
