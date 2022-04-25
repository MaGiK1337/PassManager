module com.company {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.company to javafx.fxml;
    exports com.company.controllers;
    opens com.company.controllers to javafx.fxml;
    exports com.company;
    opens com.company.userdata to javafx.fxml;
    exports com.company.userdata;
}