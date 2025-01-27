package br.com.compass.utils;
import com.password4j.Password;

public class Argon2PasswordHasher {

    // Hash the password
    public static String hashPassword(String plainPassword) {
        return Password.hash(plainPassword).withArgon2().getResult();
    }

    // Verify the password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return Password.check(plainPassword, hashedPassword).withArgon2();
    }

}