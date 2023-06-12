module main.uzd03 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports method1;
    opens method1 to javafx.fxml;
    exports method2;
    opens method2 to javafx.fxml;
    exports method3;
    opens method3 to javafx.fxml;
}