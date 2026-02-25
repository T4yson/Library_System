package com.exemplo.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "";
    private static final String User = "";
    private static final String Password = "";

    public static Connection connection () throws SQLException {
        return DriverManager.getConnection(URL, User, Password);
    }
}
