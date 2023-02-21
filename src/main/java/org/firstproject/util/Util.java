package org.firstproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection dbConnection() {
        // db mySQL get connection
        try {
            String userName = "root"; // connection username
            String password = "626871"; // connection pass
            String connectionURL = "jdbc:mysql://localhost:3306/usersdb";
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting db connection " + e);
        }
    }

}
