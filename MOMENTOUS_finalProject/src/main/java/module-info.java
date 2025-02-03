module com.example.momentous.momentous_finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires mysql.connector.j;
   // requires java.mail;
    requires net.sf.jasperreports.core;
    requires javax.mail.api;

    opens com.example.momentous.momentous_finalproject.dto.tm to javafx.base;
    opens com.example.momentous.momentous_finalproject.controller to javafx.fxml;
    exports com.example.momentous.momentous_finalproject;
}