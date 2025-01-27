package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.dao.UserDAO;
import br.com.compass.utils.UserValidator;
import br.com.compass.utils.Argon2PasswordHasher;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean createUser(String cpf,
                              String name,
                              String plainPassword,
                              String phoneNumber,
                              String dateOfBirth) {

        boolean isValidUser = UserValidator.isValidCPF(cpf) &&
                UserValidator.isValidName(name) &&
                UserValidator.isValidPassword(plainPassword) &&
                UserValidator.isValidPhoneNumber(phoneNumber) &&
                UserValidator.isValidDateOfBirth(dateOfBirth);

        if (isValidUser) {
            String hashedPassword = Argon2PasswordHasher.hashPassword(plainPassword);
            User user = new User(cpf, hashedPassword, name, dateOfBirth, phoneNumber);

            return userDAO.createUser(user);
        } else {
            System.out.println("Error: User data is invalid.");
            return false;
        }
    }
}
