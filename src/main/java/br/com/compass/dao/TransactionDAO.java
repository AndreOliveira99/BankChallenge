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

    public boolean transfer(int sourceAccountId, int targetAccountId, double amount) {
        String withdrawSQL = "UPDATE accounts SET account_balance = account_balance - ? WHERE account_id = ? AND account_balance >= ?";
        String depositSQL = "UPDATE accounts SET account_balance = account_balance + ? WHERE account_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // start transaction

            try (PreparedStatement withdrawStatement = connection.prepareStatement(withdrawSQL)) {
                withdrawStatement.setDouble(1, amount);
                withdrawStatement.setInt(2, sourceAccountId);
                withdrawStatement.setDouble(3, amount);

                int rowsUpdated = withdrawStatement.executeUpdate();
                if (rowsUpdated == 0) {
                    connection.rollback(); // Revert transaction
                    return false; // Failed to withdraw funds
                }
            }

            try (PreparedStatement depositStatement = connection.prepareStatement(depositSQL)) {
                depositStatement.setDouble(1, amount);
                depositStatement.setInt(2, targetAccountId);

                depositStatement.executeUpdate();
            }

            connection.commit(); // Confirms transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}