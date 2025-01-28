package br.com.compass.form;

import br.com.compass.dao.TransactionDAO;
import br.com.compass.dao.AccountDAO;
import br.com.compass.model.Account;
import br.com.compass.model.User;
import br.com.compass.model.Transaction;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.List;

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
        System.out.println("Making deposit.");
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

    public void transferForm(Scanner scanner, User user) {
        System.out.println("Select your account:");
        Account sourceAccount = AccountFormHandler.selectAccountForm(scanner, user);

        System.out.print("Enter the target account's number: ");
        String targetAccountNumber = scanner.next();
        Account targetAccount = accountDAO.getAccountByNumber(targetAccountNumber);

        if (targetAccount == null) {
            System.out.println("Target account not found. Please check the account number and try again.");
            return;
        }

        if (sourceAccount.getAccountNumber().equals(targetAccountNumber)) {
            System.out.println("You cannot transfer to the same account.");
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        double amount = 0;
        boolean validAmount = false;

        while (!validAmount) {
            try {
                String amountString = scanner.next();
                if (amountString.matches("^\\d+(\\.\\d{1,2})?$")) {
                    amount = Double.parseDouble(amountString);
                    if (amount <= 0) {
                        System.out.println("The amount must be greater than zero. Try again.");
                    } else if (sourceAccount.getBalance() < amount) {
                        System.out.println("Insufficient balance: " + sourceAccount.getBalance());
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

        boolean success = transactionDAO.transfer(sourceAccount.getAccountId(), targetAccount.getAccountId(), amount);

        if (success) {
            System.out.println("Transfer completed successfully!");
        } else {
            System.out.println("Transfer failed. Please try again.");
        }
    }

    public void getBankStatementForm(Scanner scanner, User user) {

        Account account = AccountFormHandler.selectAccountForm(scanner, user);

        System.out.println("Fetching bank statement for account: " + account.getAccountNumber());


        List<Transaction> transactions = transactionDAO.getTransactionsForAccount(account.getAccountId());

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("======= Bank Statement =======");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("-----------------------------------------");

        for (Transaction transaction : transactions) {

            String transactionType;
            switch (transaction.getTransactionTypeId()) {
                case 1:
                    transactionType = "Withdraw";
                    break;
                case 2:
                    transactionType = "Deposit";
                    break;
                case 3:
                    transactionType = "Transfer";
                    break;
                default:
                    transactionType = "Unknown";
            }


            String transactionDetails = String.format(
                    "Transaction ID: %d | Type: %s | Source: %s | Destination: %s | Amount: %.2f | Date: %s",
                    transaction.getTransactionId(),
                    transactionType,
                    transaction.getSourceAccountId() == account.getAccountId() ? "YOU" : "OTHER",
                    transaction.getDestinationAccountId() == account.getAccountId() ? "YOU" : "OTHER",
                    transaction.getAmount(),
                    transaction.getTransactionDate()
            );
            System.out.println(transactionDetails);
        }
        System.out.println("-----------------------------------------");
        System.out.println("=========================================");
    }

}
