package com.it481.assignment2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NorthwindDB {
    private Connection connection = null;
    private final String connectionString;
    private List<String> customerNames;

    public NorthwindDB(String connectionString) {
        this.connectionString = connectionString;
        try {
            connection = getConnection( connectionString );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Connection getConnection(String dBUrl) throws SQLException {
        return DriverManager.getConnection( dBUrl );
    }

    public List<String> getCustomerNames() throws SQLException {
        if (connection == null) {
            try {
                connection = getConnection( connectionString );
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }
        customerNames = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet customers = statement.executeQuery( "SELECT ContactName FROM Customers" );
        while (customers.next()) {
            String originalNameString = customers.getString("ContactName");
            // conversion necessary to display names properly in the ListView
            String newNameString = new String(originalNameString.getBytes(), UTF_8 );
            customerNames.add(newNameString);
        }
        customers.close();
        return customerNames;
    }

    public int getCustomerCount() throws SQLException {
        getCustomerNames();
        return customerNames.size();
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}