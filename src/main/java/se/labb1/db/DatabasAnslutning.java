package se.labb1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasAnslutning {

    private static final String URL = "jdbc:mysql://localhost:3306/webbshop?characterEncoding=UTF-8";
    private static final String ANVANDARE = "root";
    private static final String LOSENORD = "root";

    public static Connection hamtaAnslutning() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException fel) {
            throw new SQLException("Hittade inte MySQL-drivrutinen.", fel);
        }
        return DriverManager.getConnection(URL, ANVANDARE, LOSENORD);
    }
}
