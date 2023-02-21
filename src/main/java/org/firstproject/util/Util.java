package org.firstproject.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String PROPERTIES_FILE = "database.properties";

    public static Connection dbConnection() {
        Properties properties = loadProperties(PROPERTIES_FILE);
        try {
            String userName = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String connectionURL = properties.getProperty("db.url");
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            // Handle the exception more gracefully here
            throw new RuntimeException("Error getting db connection " + e);
        }
    }

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = Util.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file " + fileName, e);
        }
        return properties;
    }
}
