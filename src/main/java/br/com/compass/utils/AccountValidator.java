package br.com.compass.utils;

public class AccountValidator {
    public static boolean isValidAccountType(Integer accountTypeId, Integer userId) {
        if (accountTypeId < 1 || accountTypeId > 5) {
            System.out.println("Select a valid account type.");
            return false;
        }
        else {
            return true;
        }
    }
}
