package tn.edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    private String url = "jdbc:mysql://localhost:3306/esprit";
    private String user = "root";
    private String password = "Ness";

    private DataSource(){
        try {
            // Load the MySQL JDBC driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB!");
        } catch (SQLException | ClassNotFoundException ex) {
            // Print the error message if connection fails
            System.out.println("Connection failed: " + ex.getMessage());
        }
    }

    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection(){
        return this.cnx;
    }
}
