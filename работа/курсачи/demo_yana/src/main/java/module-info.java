module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.demo_yana to javafx.fxml;
    exports com.example.demo_yana;
}