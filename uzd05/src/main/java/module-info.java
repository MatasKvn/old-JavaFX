module main.uzd05 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports server;
    opens server to javafx.fxml;
    opens applications to javafx.fxml;
    exports applications;
    opens singletons to javafx.fxml;
    exports singletons;
}