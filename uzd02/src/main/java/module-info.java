module uzd02 {
    requires javafx.controls;
    requires javafx.fxml;


    opens Main to javafx.fxml;
    exports Main;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Calculators;
    opens Calculators to javafx.fxml;
}