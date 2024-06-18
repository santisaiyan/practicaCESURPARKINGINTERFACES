
//hay que añadir los requires de sql swing reports y lombok

module com.example.practicafrancisco {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires javafx.swing;
    requires jasperreports;

    opens com.example.practicafrancisco to javafx.fxml;
    exports com.example.practicafrancisco;
}