package br.com.compass.dao;

import br.com.compass.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {

    // Make deposits method
    public boolean deposit(int accountId, double amount) {
        String sql = "UPDATE accounts SET account_balance = account_balance + ? WHERE account_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setInt(2, accountId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Make withdraws method
    public boolean withdraw(int accountId, double amount) {
        String sql = "UPDATE accounts SET account_balance = account_balance - ? WHERE account_id = ? AND account_balance >= ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setInt(2, accountId);
            statement.setDouble(3, amount); // Makes sure balance is not negative

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}