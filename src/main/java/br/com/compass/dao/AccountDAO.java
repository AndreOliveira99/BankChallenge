package br.com.compass.dao;

import br.com.compass.model.Account;
import br.com.compass.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
