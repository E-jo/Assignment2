package com.it481.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Assignment2Controller {
    private final String connectionString =
            "jdbc:sqlserver://localhost:1433;" +
            "databaseName=Northwind;integratedSecurity=true;" +
            "encrypt=true;trustServerCertificate=true";
    private final NorthwindDB db = new NorthwindDB( connectionString );
    @FXML
    private Label countLabel;

    @FXML
    private ListView<String> customerList;

    @FXML
    protected void onLastNamesButtonClick() {
        try {
            List<String> lastNames = getLastNames(db.getCustomerNames());
            ObservableList<String> lNames = FXCollections.observableArrayList(lastNames);
            customerList.setItems( (lNames) );
            countLabel.setText( "Total: " + db.getCustomerCount() );
        } catch (SQLException e) {
            System.err.println( e );
        }
    }

    private List<String> getLastNames(List<String> customerNames) {
        List<String> lastNames = new ArrayList<>();
        for (String name : customerNames) {
            String[] names = name.split("\\s+");
            String lastName = names[1];
            // for compound last names
            if (names.length > 2) {
                lastName += " ";
                for (int i = 2; i < names.length; i++) {
                    lastName += names[i];
                    if (i != names.length - 1) {
                        lastName += " ";
                    }
                }
            }
            lastNames.add(lastName);
        }
        return lastNames;
    }

    @FXML
    protected void onFullNamesButtonClick() {
        try {
            List<String> customerNames = db.getCustomerNames();
            ObservableList<String> names = FXCollections.observableArrayList(customerNames);
            customerList.setItems( (names) );
            countLabel.setText( "Total: " + db.getCustomerCount() );
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @FXML
    public void onExitButtonClick() {
        db.close();
        System.out.println("connection closed");
        System.exit(0);
    }

}