package br.com.compass.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserValidator {

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            System.out.println("CPF should have 11 digits.");
            return false;
        } else if (!cpf.matches("\\d+")) {
            System.out.println("CPF should only contain digits. Ex: 12345678901");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Name required.");
            return false;
        } else if (!name.matches("^[\\p{L}\\s]+$")) {
            System.out.println("Name should only contain letters and spaces.");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            System.out.println("Password should have at least 6 characters.");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidHashedPassword(String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10 && phoneNumber.length() != 11) {
            System.out.println("Phone number (Including Area Code) should have 10 or 11 digits. Ex: 99999999999");
            return false;
        } else if (!phoneNumber.matches("\\d+")) {
            System.out.println("Phone number should only contain digits.");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isValidDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.length() != 10) {
            System.out.println("Date of Birth should have 10 digits. Ex: 9999-99-99");
            return false;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate informedDate = LocalDate.parse(dateOfBirth, formatter); // Parses the date
                LocalDate today = LocalDate.now();
                LocalDate eighteenYearsAgo = today.minusYears(18);

                // Check if the date of birth is before or equal to the valid date
                if (!informedDate.isBefore(eighteenYearsAgo) && !informedDate.isEqual(eighteenYearsAgo)) {
                    System.out.println("Date of Birth invalid. Required to be over 18 years old.");
                    return false; // Date is not at least 18 years in the past
                }
                else {
                    return true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Date of Birth should be in the format yyyy-mm-dd.");
                return false; // Invalid date format or values
            }
        }
    }

}
