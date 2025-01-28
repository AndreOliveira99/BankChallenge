package br.com.compass.dao;

import br.com.compass.model.User;
import br.com.compass.database.DatabaseConnection;
import br.com.compass.utils.JbcryptPasswordHasher;
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
    public boolean authenticate(String cpf, String plainPassword) {
        String sql = "SELECT hashed_password FROM users WHERE cpf = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Perform Database Query
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();

            // If CPF found compare password
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("hashed_password");
                return JbcryptPasswordHasher.verifyPassword(plainPassword, storedHashedPassword);
            }

            // Else, returns false
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Query Database and retrieve User Object
    public User getUserByCpf(String cpf) {
        String sql = "SELECT * FROM users WHERE cpf = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Define o parâmetro CPF na query SQL
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();

            // Se o resultado da consulta encontrar o CPF
            if (resultSet.next()) {
                // Cria e preenche um objeto User com os dados da consulta

                return new User(resultSet.getString("cpf"),
                        resultSet.getString("hashed_password"),
                        resultSet.getString("name"),
                        resultSet.getString("date_of_birth"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("user_id")
                );
            }

            // Caso não encontre, retorna nulo
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Caso haja exceção, também retorna nulo
        }
    }
}