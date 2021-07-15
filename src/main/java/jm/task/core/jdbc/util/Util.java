package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "jS*Po}{@2";
    private static  String tableName ;



    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
