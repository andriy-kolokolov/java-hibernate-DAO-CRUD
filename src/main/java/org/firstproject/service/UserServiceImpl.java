package org.firstproject.service;

import org.firstproject.model.User;
import org.firstproject.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users(
                    id BIGINT NOT NULL AUTO_INCREMENT KEY,
                    userName VARCHAR(50),
                    userLastName VARCHAR(50),
                    userAge TINYINT);""");
        }
        // console log
        System.out.println("Users table created...");
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users;");
            // console log
            System.out.println("Users table dropped...");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String saveUserQuery = String.format("""
                INSERT INTO users (userName, userLastName, userAge)
                VALUES('%s','%s','%2d');""", name, lastName, age);
        // console log
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate(saveUserQuery);
            System.out.println("User added to database: " + name + " " + lastName);
        }

    }

    public void removeUserById(long id) throws SQLException {
        String removeUserByIdQuery = String.format("DELETE FROM users WHERE id=%2d;", id);
        try (Connection connection = Util.dbConnection()) {
            connection.createStatement().executeUpdate(removeUserByIdQuery);
            // console log
            System.out.println("User with id: " + id + " removed from database...");
        }
    }

    public List<User> getAllUsers() throws SQLException {
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

    public void cleanUsersTable() throws SQLException {
        try(Connection connection = Util.dbConnection()){
            connection.createStatement().executeUpdate("DELETE FROM users;");
            // console log
            System.out.println("Users table deleted...");
        }
    }
}
