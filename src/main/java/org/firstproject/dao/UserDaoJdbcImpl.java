package org.firstproject.dao;

import org.firstproject.model.jdbc.User;
import org.firstproject.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    public void createUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users(
                    id BIGINT NOT NULL AUTO_INCREMENT KEY,
                    userName VARCHAR(50),
                    userLastName VARCHAR(50),
                    userAge TINYINT);""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // console log
        System.out.println("Users table created...");
    }

    public void dropUsersTable() {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users;");
            // console log
            System.out.println("Users table dropped...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserQuery = String.format("""
                INSERT INTO users (userName, userLastName, userAge)
                VALUES('%s','%s','%2d');""", name, lastName, age);
        // console log
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate(saveUserQuery);
            System.out.println("User added to database: " + name + " " + lastName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String removeUserByIdQuery = String.format("DELETE FROM users WHERE id=%2d;", id);
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate(removeUserByIdQuery);
            // console log
            System.out.println("User with id: " + id + " removed from database...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersFromDbList = new ArrayList<>();
        try(Connection connection = Util.dbConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("userName"),
                        resultSet.getString("userLastName"),
                        resultSet.getByte("userAge"));
                user.setId(Long.valueOf(resultSet.getString("id")));
                usersFromDbList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // console log
        if (!usersFromDbList.isEmpty()){
            System.out.println("Getting users from database... > ");
            for (User user : usersFromDbList){
                System.out.println(user.toString());
            }
        }
        else {
            System.out.println("Database is empty...");
        }
        return usersFromDbList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.dbConnection()){
            connection.createStatement().executeUpdate("DELETE FROM users;");
            // console log
            System.out.println("Users table deleted...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
