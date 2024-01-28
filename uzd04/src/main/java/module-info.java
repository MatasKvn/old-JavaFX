module main.uzd04 {
    requires javafx.controls;
    requires javafx.fxml;
    requires poi.ooxml;
    requires poi;
    requires org.apache.pdfbox;


    opens main to javafx.fxml;
    exports main;
    opens controllers to javafx.fxml;
    exports controllers;
    opens classes to javafx.fxml;
    exports classes;
}