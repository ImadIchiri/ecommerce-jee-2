package com.ecommerce.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    //private static String url = "jdbc:mysql://sql8.freemysqlhosting.net/sql8640886";
    //private static String user = "sql8640886";
    //private static String password = "gwEbscYA4U";
	
	private static String url = "jdbc:mysql://srv914.hstgr.io:3306/u899963546_ecommerce_j2ee";
    private static String user = "u899963546_ichiri_imad";
    private static String password = "PS3pEempHzM3L@c";
    private static Connection connection;
    
    public static Connection connectToDB() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

        return connection;
    }
}

