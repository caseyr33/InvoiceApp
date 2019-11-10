package model;


import java.sql.DriverManager;
import java.sql.Connection;

public class SQLiteConnection
{
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            final Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Sean\\Documents\\stockton\\Fall 2019\\Software engineering\\InvoiceApp\\hankdb.db");
            return conn;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}