package br.com.compass.dao;

import br.com.compass.database.DatabaseConnection;
import br.com.compass.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Make deposits method
    public boolean deposit(int accountId, double amount) {
        String updateBalanceSQL = "UPDATE accounts SET account_balance = account_balance + ? WHERE account_id = ?";
        String insertTransactionSQL = "INSERT INTO transactions (transaction_amount, source_account_id, transaction_type_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(updateBalanceSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    connection.rollback();
                    return false; // Falha ao atualizar o saldo
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(insertTransactionSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);
                statement.setInt(3, 2); // transaction_type_id = 2 para depósito
                System.out.println(accountId);
                statement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Make withdraws method
    public boolean withdraw(int accountId, double amount) {
        String updateBalanceSQL = "UPDATE accounts SET account_balance = account_balance - ? WHERE account_id = ? AND account_balance >= ?";
        String insertTransactionSQL = "INSERT INTO transactions (transaction_amount, source_account_id, transaction_type_id) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(updateBalanceSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);
                statement.setDouble(3, amount);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    connection.rollback();
                    return false; // Saldo insuficiente ou falha ao atualizar
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(insertTransactionSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, accountId);
                statement.setInt(3, 1); // transaction_type_id = 1 para retirada

                statement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean transfer(int sourceAccountId, int targetAccountId, double amount) {
        String withdrawSQL = "UPDATE accounts SET account_balance = account_balance - ? WHERE account_id = ? AND account_balance >= ?";
        String depositSQL = "UPDATE accounts SET account_balance = account_balance + ? WHERE account_id = ?";
        String insertTransactionSQL = "INSERT INTO transactions (transaction_amount, source_account_id, recipient_account_id, transaction_type_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);


            try (PreparedStatement statement = connection.prepareStatement(withdrawSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, sourceAccountId);
                statement.setDouble(3, amount);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(depositSQL)) {
                statement.setDouble(1, amount);
                statement.setInt(2, targetAccountId);

                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(insertTransactionSQL)) {

                statement.setDouble(1, amount);
                statement.setInt(2, sourceAccountId);
                statement.setInt(3, targetAccountId);
                statement.setInt(4, 3); // transaction_type_id = 3 para transferência
                statement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionsForAccount(int accountId) {
        List<Transaction> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions WHERE source_account_id = ? OR recipient_account_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, accountId);
            stmt.setInt(2, accountId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setTransactionTypeId(rs.getInt("transaction_type_id")); // Tipo de transação
                transaction.setSourceAccountId(rs.getInt("source_account_id"));
                transaction.setDestinationAccountId(rs.getInt("recipient_account_id"));
                transaction.setAmount(rs.getDouble("transaction_amount"));
                transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

}