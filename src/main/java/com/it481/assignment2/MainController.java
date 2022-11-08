package com.it481.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private User user;
    @FXML
    private Label countLabel;
    @FXML
    private ListView<String> display;

    public void setUser(User user) {
        // the four fields for the connection string
        // are encapsulated in a "User" class
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private String getConnectionString() {
        // if user is not logged in (because they ran the main
        // view first, instead of the login view), return
        // them to login view
        if (getUser() == null) {
            return null;
        }
        return "jdbc:sqlserver://" + user.getServer() +
                ";databaseName=" + user.getDb() +
                ";encrypt=true;trustServerCertificate=true;" +
                "user=" + user.getName() +
                ";password=" + user.getPassword();
    }
    public NorthwindDB getDb() {
        String connectionString = getConnectionString();
        if (connectionString == null) {
            try {
                onLogoutButtonClick();
            } catch (IOException ignored) {
            }
            return null;
        }
        return new NorthwindDB( connectionString );
    }

    // leftover from unit 2; could be removed
    @FXML
    protected void onLastNamesButtonClick() {
        NorthwindDB db = getDb();
        try {
            List<String> lastNames = getLastNames(db.getCustomerNames());
            ObservableList<String> lNames = FXCollections.observableArrayList(lastNames);
            display.setItems( (lNames) );
            countLabel.setText( "Total: " + db.getCustomerCount() );
        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        } finally {
            db.close();
        }
    }

    // leftover from unit 2; could be removed
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
    protected void onCustomersButtonClick() {
        NorthwindDB db = getDb();
        if (db != null) {
            try {
                List<String> customerNames = db.getCustomerNames();
                ObservableList<String> names = FXCollections.observableArrayList( customerNames );
                display.setItems( (names) );
                countLabel.setText( "Total: " + db.getCustomerCount() );
            } catch (SQLException e) {
                Alert a = new Alert( Alert.AlertType.ERROR );
                a.setContentText( user.getName() +
                        " does not have permission to view Customers" );
                a.show();
                System.err.println( e.getMessage() );
            } finally {
                db.close();
            }
        }
    }

    @FXML
    public void onLogoutButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));

        Stage stage = new Stage( StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setMinHeight( 300 );
        stage.setTitle( "Login" );
        stage.setResizable( false );
        stage.show();

        Stage oldStage = (Stage) countLabel.getScene().getWindow();
        oldStage.close();
    }

    public void onOrdersButtonClick() {
        NorthwindDB db = getDb();
        if (db != null) {
            try {
                List<String> orders = db.getOrders();
                ObservableList<String> names = FXCollections.observableArrayList( orders );
                display.setItems( (names) );
                countLabel.setText( "Total: " + orders.size() );
            } catch (SQLException e) {
                Alert a = new Alert( Alert.AlertType.ERROR );
                a.setContentText( user.getName() +
                        " does not have permission to view Orders" );
                a.show();
                System.err.println( e.getMessage() );
            } finally {
                db.close();
            }
        }
    }

    public void onEmployeesButtonClick() {
        NorthwindDB db = getDb();
        if (db != null) {
            try {
                List<String> employees = db.getEmployeeNames();
                ObservableList<String> names = FXCollections.observableArrayList( employees );
                display.setItems( (names) );
                countLabel.setText( "Total: " + employees.size() );
            } catch (SQLException e) {
                Alert a = new Alert( Alert.AlertType.ERROR );
                a.setContentText( user.getName() +
                        " does not have permission to view Employees" );
                a.show();
                System.err.println( e.getMessage() );
            } finally {
                db.close();
            }
        }
    }
}