package br.com.compass.model;

public class Account {
    private Integer accountTypeId;
    private double balance;
    private String accountNumber;
    private Integer userId;
    private Integer accountId;

    // Constructor
    public Account(Integer accountTypeId, double balance, String accountNumber, Integer userId, Integer accountId) {
        this.accountTypeId = accountTypeId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.accountId = accountId;
    }

    // Getters and Setters
    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getUserId() {
        return userId;
    }

    public int getAccountId() {
        return accountId;
    }
}
