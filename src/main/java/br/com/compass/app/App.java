package br.com.compass.app;

import br.com.compass.dao.UserDAO;
import br.com.compass.dao.AccountDAO;
import br.com.compass.form.AccountFormHandler;
import br.com.compass.model.Account;
import br.com.compass.model.User;
import br.com.compass.form.UserFormHandler;
import br.com.compass.form.TransactionFormHandler;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);
        
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();

        User userLoggedIn = null;
        Account accountLoggedIn = null;

        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Login
                    String[] credentials = UserFormHandler.loginForm(scanner);
                    boolean loginSuccessful = userDAO.authenticate(credentials[0], credentials[1]);

                    if (loginSuccessful) {
                        System.out.println("Login successful!");
                        userLoggedIn = userDAO.getUserByCpf(credentials[0]);
                        bankMenu(scanner, userLoggedIn);
                    } else {
                        System.out.println("Invalid CPF or password.");
                        System.out.println("Please try again or select a different option");
                        mainMenu(scanner);
                    }
                    return;
                case 2:
                    // Create user
                    User user = UserFormHandler.createUserForm(scanner);
                    boolean createUserSuccessful = userDAO.createUser(user);

                    Account account = AccountFormHandler.createAccountForm(scanner, user);
                    boolean createAccountSuccessful = accountDAO.createAccount(account, user.getCpf());

                    if (createUserSuccessful && createAccountSuccessful) {
                        System.out.println("Account created successfully!");
                        System.out.println("Account number: " + account.getAccountNumber());
                    } else {
                        System.out.println("Error creating account.");
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner, User userLoggedIn) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            TransactionFormHandler transactionHandler = new TransactionFormHandler();

            switch (option) {
                case 1:
                    System.out.println("Deposit.");
                    transactionHandler.depositForm(scanner, userLoggedIn);
                    break;
                case 2:
                    System.out.println("Withdraw.");
                    transactionHandler.withdrawForm(scanner, userLoggedIn);
                    break;
                case 3:
                    System.out.println("Check Balance.");
                    transactionHandler.checkBalanceForm(scanner, userLoggedIn);
                    break;
                case 4:

                    System.out.println("Transfer.");
                    break;
                case 5:
                    // ToDo...
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
    
}
