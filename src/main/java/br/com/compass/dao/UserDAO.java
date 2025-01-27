package br.com.compass.dao;

import br.com.compass.model.User;
import br.com.compass.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // Insert a new user into the database
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (cpf, hashed_password, name, date_of_birth, phone_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getCpf());
            statement.setString(2, user.getHashedPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getDateOfBirth());
            statement.setString(5, user.getPhoneNumber());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Authenticate a user based on CPF and password
    public boolean authenticate(String cpf, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE cpf = ? AND hashed_password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a match is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}