module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    exports com.example.demo1.Controllers;
    exports com.example.demo1.Services;
    exports com.example.demo1.Repositories;
    exports com.example.demo1.Utils;
    exports com.example.demo1.Entities;
}