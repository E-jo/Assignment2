package com.it481.assignment2;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NorthwindDB {
    private Connection connection = null;
    public final String connectionString;
    private List<String> customerNames;

    public NorthwindDB(String connectionString) {
        this.connectionString = connectionString;
        try {
            connection = getConnection( connectionString );
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getConnectionString() {
        return this.connectionString;
    }

    public Connection getConnection(String dBUrl) throws SQLException {
        return DriverManager.getConnection( dBUrl );
    }

    public List<String> getCustomerNames() throws SQLException {
        // always try one more time to connect
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

    public List<String> getEmployeeNames() throws SQLException {
        // again, always try one more time to connect
        if (connection == null) {
            try {
                connection = getConnection( connectionString );
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return null;
            }
        }

        // collect the last names and first names separately, then combine

        List<String> lastNames = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet empLastNames = statement.executeQuery( "SELECT LastName FROM Employees" );
        while (empLastNames.next()) {
            String originalNameString = empLastNames.getString("LastName");
            // conversion necessary to display names properly in the ListView
            String newNameString = new String(originalNameString.getBytes(), UTF_8 );
            lastNames.add(newNameString);
        }
        empLastNames.close();

        List<String> firstNames = new ArrayList<>();
        ResultSet empFirstNames = statement.executeQuery( "SELECT FirstName FROM Employees" );
        while (empFirstNames.next()) {
            String originalNameString = empFirstNames.getString("FirstName");
            // conversion necessary to display names properly in the ListView
            String newNameString = new String(originalNameString.getBytes(), UTF_8 );
            firstNames.add(newNameString);
        }
        empFirstNames.close();

        List<String> empFullNames = new ArrayList<>();
        for (int i = 0; i < lastNames.size(); i++) {
            empFullNames.add( firstNames.get(i) + " " + lastNames.get(i) );
        }
        return empFullNames;
    }

    public List<String> getOrders() throws SQLException {
        // you know the drill... always try one more time to connect
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
        ResultSet customers = statement.executeQuery( "SELECT ShipName FROM Orders" );
        while (customers.next()) {
            String originalNameString = customers.getString("ShipName");
            // conversion necessary to display names properly in the ListView (I know, right?)
            String newNameString = new String(originalNameString.getBytes(), UTF_8 );
            customerNames.add(newNameString);
        }
        customers.close();
        return customerNames;
    }

    // another holdover from unit 2... counts are no longer handled here; could be removed
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