package br.com.compass.form;

import br.com.compass.model.Account;
import br.com.compass.model.User;
import br.com.compass.utils.AccountValidator;

import java.util.Scanner;

public class AccountFormHandler {
    public static Account createAccountForm(Scanner scanner, User user) {

        boolean accountTypeValid = false;
        Integer accountType = null;

        while (!accountTypeValid) {
            System.out.println("Enter Account Type:");
            System.out.println("======= Account Types =======");
            System.out.println("|| 1. Corrente             ||");
            System.out.println("|| 2. Poupança             ||");
            System.out.println("|| 3. Salário              ||");
            System.out.println("|| 4. Universitária        ||");
            System.out.println("|| 5. Digital              ||");
            System.out.println("=============================");

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

    public static String[] loginForm(Scanner scanner) {
        System.out.println("Enter CPF:");
        String cpf = scanner.next();

        System.out.println("Enter Password:");
        String plainPassword = scanner.next();

        return new String[]{cpf, plainPassword};
    }
}
