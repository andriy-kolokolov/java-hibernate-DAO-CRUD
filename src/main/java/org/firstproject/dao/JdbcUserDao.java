package org.firstproject.dao;

import org.firstproject.model.jdbc.User;
import org.firstproject.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    public void createUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Users(
                    id BIGINT NOT NULL AUTO_INCREMENT KEY,
                    userName VARCHAR(50),
                    userLastName VARCHAR(50),
                    userAge TINYINT);""");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user table", e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS Users;");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop user table", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.dbConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (userName, userLastName, userAge) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.dbConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove user by id", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersFromDbList = new ArrayList<>();
        try (Connection connection = Util.dbConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("userName"),
                        resultSet.getString("userLastName"),
                        resultSet.getByte("userAge"));
                user.setId(resultSet.getLong("id"));
                usersFromDbList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all users", e);
        }
        return usersFromDbList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("DELETE FROM Users");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean user table", e);
        }
    }
}
