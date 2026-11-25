package model;

import exceptions.AccountException;

public class BankAccount {
    private String userId;
    private String customerName;
    private String password;
    private double balance;

    public BankAccount(String userId, String customerName, String password, double balance) {
        if (userId == null || userId.isBlank()) {
            throw new AccountException("User ID cannot be empty");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new AccountException("Customer name cannot be empty");
        }
        validatePassword(password);
        if (balance < 0) {
            throw new AccountException("Balance cannot be negative");
        }
        this.userId = userId;
        this.customerName = customerName;
        this.password = password;
        this.balance = balance;
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new AccountException("Password must be at least 8 characters long");
        }
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        if (!hasDigit) {
            throw new AccountException("Password must contain at least one digit");
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new AccountException("Deposit amount must be greater than 0");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new AccountException("Withdrawal amount must be greater than 0");
        }
        if (amount > balance) {
            throw new AccountException("Insufficient funds");
        }
        balance -= amount;
    }
}
