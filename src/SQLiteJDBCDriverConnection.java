/**
 * Inventory Management System
 * Author: Fatjon Tushe
 * December 2018
 * Project For Personal Portfolio
 * */

import java.sql.*;

public class SQLiteJDBCDriverConnection {

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:Database\\Inventory.db";

    public static Connection connect() {
        Connection c = null;

        try {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");

        return c;
    }
}