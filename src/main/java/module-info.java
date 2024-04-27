module com.example.assignment2.controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.xerial.sqlitejdbc;

//    opens com.example.assignment2 to javafx.fxml;
//    exports com.example.assignment2;

    exports com.example.assignment2.controllers;
    opens com.example.assignment2.controllers to javafx.fxml;


}