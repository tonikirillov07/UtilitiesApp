module com.ds.utilitiesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires java.sql;
    requires jdk.compiler;

    opens com.ds.utilitiesapp to javafx.fxml;
    exports com.ds.utilitiesapp;
    exports com.ds.utilitiesapp.controllers;
    opens com.ds.utilitiesapp.controllers to javafx.fxml;
}