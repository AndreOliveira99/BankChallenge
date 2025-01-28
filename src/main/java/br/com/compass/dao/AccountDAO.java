package br.com.compass.dao;

import br.com.compass.model.Account;
import br.com.compass.database.DatabaseConnection;
import br.com.compass.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO {
    // Insert a new account into the database
    public boolean createAccount(Account account, String userCpf) {
        String sql = "INSERT INTO accounts (account_type_id, account_balance, account_number, user_id) " +
                "VALUES (?, ?, ?, (SELECT user_id FROM users WHERE cpf = ?))";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, account.getAccountTypeId());
            statement.setDouble(2, account.getBalance());
            statement.setString(3, account.getAccountNumber());
            statement.setString(4, userCpf);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Query Database and retrieve all available accounts for a given user_id
    public List<Account> getAvaliableAccounts(Integer userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getInt("account_type_id"),
                        resultSet.getDouble("account_balance"),
                        resultSet.getString("account_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("account_id")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt("account_type_id"),
                        resultSet.getDouble("account_balance"),
                        resultSet.getString("account_number"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("account_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Returns null if account not found
    }

}
