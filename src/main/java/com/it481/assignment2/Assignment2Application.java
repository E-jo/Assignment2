package com.it481.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Assignment2Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( Assignment2Application.class.getResource( "hello-view.fxml" ) );
        Scene scene = new Scene( fxmlLoader.load(), 320, 400 );
        stage.setTitle( "Northwind DB: Customers" );
        stage.setScene( scene );
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}