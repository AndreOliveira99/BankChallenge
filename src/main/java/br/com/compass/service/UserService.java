package br.com.compass.service;

import br.com.compass.model.User;
import br.com.compass.dao.UserDAO;
import br.com.compass.utils.UserValidator;
import br.com.compass.utils.JbcryptPasswordHasher;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean saveUser (User user, String plainPassword) {

        String hashedPassword = JbcryptPasswordHasher.hashPassword(plainPassword);

        if (isValidUser(user)) {
            return userDAO.createUser(user);
        } else {
            System.out.println("Error: User data is invalid.");
            return false;
        }
    }

    private boolean isValidUser(User user) {
        return UserValidator.isValidCPF(user.getCpf()) &&
                UserValidator.isValidName(user.getName()) &&
                UserValidator.isValidHashedPassword(user.getHashedPassword()) &&
                UserValidator.isValidPhoneNumber(user.getPhoneNumber()) &&
                UserValidator.isValidDateOfBirth(user.getDateOfBirth());
    }
}
