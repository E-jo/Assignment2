module com.it481.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.it481.assignment2 to javafx.fxml;
    exports com.it481.assignment2;
}