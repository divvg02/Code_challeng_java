package com.hms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
    private static Connection connection;

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        ResourceBundle resMySQL = ResourceBundle.getBundle("mysql");

        String url = resMySQL.getString("url");
        String username = resMySQL.getString("username");
        String password = resMySQL.getString("password");
        String driver = resMySQL.getString("driver");

        Class.forName(driver);//loading JDBC driver into memory

        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established.");
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}