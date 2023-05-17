module com.example.tugasbesarpbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.tugasbesarpbo to javafx.fxml;
    exports com.example.tugasbesarpbo;
}