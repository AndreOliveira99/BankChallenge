package br.com.compass.form;

import br.com.compass.dao.TransactionDAO;
import br.com.compass.dao.AccountDAO;
import br.com.compass.model.Account;
import br.com.compass.model.User;

import java.text.DecimalFormat;
import java.util.Scanner;

public class TransactionFormHandler {

    private TransactionDAO transactionDAO = new TransactionDAO();
    private AccountDAO accountDAO = new AccountDAO();

    public void depositForm(Scanner scanner, User user) {

        Account account = AccountFormHandler.selectAccountForm(scanner, user);

        System.out.print("Enter the amount to deposit: ");
        double amount = 0;
        boolean validAmount = false;
        String amountString = "";

        while (!validAmount) {
            try {
                amountString = scanner.next();
                if (amountString.matches("^\\d+(\\.\\d{1,2})?$")) {
                    amount = Double.parseDouble(amountString);
                    if (amount <= 0) {
                        System.out.println("The amount must be greater than zero. Try again.");
                    } else {
                        validAmount = true;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid amount.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }

        // Make deposit
        boolean success = transactionDAO.deposit(account.getAccountId(), amount);

        if (success) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed. Please try again.");
        }
    }

    public void withdrawForm(Scanner scanner, User user) {

        Account account = AccountFormHandler.selectAccountForm(scanner, user);

        System.out.print("Enter the amount to withdraw: ");
        double amount = 0;
        boolean validAmount = false;
        String amountString = "";

        while (!validAmount) {
            try {
                amountString = scanner.next();
                if (amountString.matches("^\\d+(\\.\\d{1,2})?$")) {
                    amount = Double.parseDouble(amountString);
                    if (amount <= 0) {
                        System.out.println("The amount must be greater than zero. Try again.");
                    } else if (account.getBalance() < amount) {
                        System.out.println("Insufficient balance: " + account.getBalance());
                    } else {
                        validAmount = true;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }

        boolean success = transactionDAO.withdraw(account.getAccountId(), amount);

        if (success) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or other error.");
        }
    }

    public void checkBalanceForm(Scanner scanner, User user) {

        Account account = AccountFormHandler.selectAccountForm(scanner, user);

        DecimalFormat df = new DecimalFormat("#.00");

        System.out.println("The current balance of account " + account.getAccountNumber() + " is: R$ " + df.format(account.getBalance()));
    }

}
