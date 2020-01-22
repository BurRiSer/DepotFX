module DepotFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    exports domain;

    opens controller;
}