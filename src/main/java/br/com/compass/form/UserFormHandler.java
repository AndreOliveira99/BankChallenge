package br.com.compass.form;

import br.com.compass.model.User;
import br.com.compass.utils.UserValidator;
import java.util.Scanner;

public class UserFormHandler {
    public static User createUserForm(Scanner scanner) {

        System.out.println("Enter CPF:");
        String cpf = scanner.next();
        while (!UserValidator.isValidCPF(cpf)) {
             System.out.println("Please try again.");
             cpf = scanner.next();
        }

        System.out.println("Enter Name:");
        String name = scanner.next();
        while (!UserValidator.isValidName(name)) {
            System.out.println("Please try again.");
            name = scanner.next();
        }

        System.out.println("Enter Date of Birth (YYYY-MM-DD):");
        String dateOfBirth = scanner.next();
        while (!UserValidator.isValidDateOfBirth(dateOfBirth)) {
            System.out.println("Please try again.");
            dateOfBirth = scanner.next();
        }

        System.out.println("Enter Phone Number:");
        String phone = scanner.next();
        while (!UserValidator.isValidPhoneNumber(phone)) {
            System.out.println("Please try again.");
            phone = scanner.next();
        }

        System.out.println("Enter Password:");
        String password = scanner.next();
        while (!UserValidator.isValidPassword(password)) {
            System.out.println("Please try again.");
            password = scanner.next();
        }

        return new User(cpf, password, name, dateOfBirth, phone);
    }

    public static String[] loginForm(Scanner scanner) {
        System.out.println("Enter CPF:");
        String cpf = scanner.next();

        System.out.println("Enter Password:");
        String password = scanner.next();

        return new String[]{cpf, password};
    }
}
