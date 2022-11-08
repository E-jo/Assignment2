package com.it481.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtServer;

    @FXML
    private TextField txtDB;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    public void onLoginButtonClick() throws IOException {
        String name = txtName.getText();
        String password = txtPassword.getText();
        String server = txtServer.getText();
        String db = txtDB.getText();

        // create a new User object with the four connection string fields
        User user = new User(name, password, server, db);

        // for testing purposes
        System.out.println("user name: " + user.getName());
        System.out.println("user password: " + user.getPassword());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));

        Stage stage = new Stage( StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setResizable( false );
        stage.setTitle( "Northwind DB" );

        // get a reference to the other controller, set the user data,
        // try to log in to the database
        MainController controller = loader.getController();
        controller.setUser(user);
        try {
            controller.getDb().getConnection( controller.getDb().getConnectionString() );
        } catch (SQLException e) {
            Alert a = new Alert( Alert.AlertType.ERROR );
            a.setContentText( "Invalid login\nUser: " +
                    user.getName() +
                    "\nPassword: " +
                    user.getPassword());
            a.show();
            System.err.println(e.getMessage());
            return;
        }

        stage.show();

        Stage oldStage = (Stage) txtName.getScene().getWindow();
        oldStage.close();
    }
}
