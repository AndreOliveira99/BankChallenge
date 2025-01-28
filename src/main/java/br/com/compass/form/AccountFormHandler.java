package br.com.compass.form;

import br.com.compass.model.Account;
import br.com.compass.model.User;
import br.com.compass.utils.AccountValidator;
import br.com.compass.dao.AccountDAO;

import java.util.List;
import java.util.Scanner;

public class AccountFormHandler {

    public static Account createAccountForm(Scanner scanner, User user) {

        boolean accountTypeValid = false;
        Integer accountType = null;

            System.out.println("Enter Account Type:");
            System.out.println("======= Account Types =======");
            System.out.println("|| 1. Corrente             ||");
            System.out.println("|| 2. Poupança             ||");
            System.out.println("|| 3. Salário              ||");
            System.out.println("|| 4. Universitária        ||");
            System.out.println("|| 5. Digital              ||");
            System.out.println("=============================");

        while (!accountTypeValid) {
            try {
                accountType = Integer.parseInt(scanner.next());
                while (!AccountValidator.isValidAccountType(accountType, user.getUserId())) {
                    System.out.println("Please try again.");
                    accountType = Integer.parseInt(scanner.next());
                }
                accountTypeValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Option! Please try again.");
            }
        }

        return new Account(accountType, 0.00, user.getCpf().concat(String.valueOf(accountType)), null, null);
    }

    public static Account selectAccountForm(Scanner scanner, User user) {

        AccountDAO accountDAO = new AccountDAO();
        boolean accountOptionValid = false;
        Integer accountOption = null;

        List<Account> accounts = accountDAO.getAvaliableAccounts(user.getUserId());

        System.out.println("======= Select Account ======");
        for (int index = 0; index < accounts.size(); index++) {
            Account account = accounts.get(index);
            System.out.println(index + ". " + account.getAccountNumber());
        }
        System.out.println("=============================");
        while (!accountOptionValid) {
            System.out.print("Choose an option: ");
            try {
                accountOption = Integer.parseInt(scanner.next());
                while (accountOption < 0 || accountOption >= accounts.size()) {
                    System.out.println("Invalid Option! Please try again.");
                    accountOption = Integer.parseInt(scanner.next());
                }
                accountOptionValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Option! Please try again.");
            }

        }

        return accounts.get(accountOption);
    }
}
