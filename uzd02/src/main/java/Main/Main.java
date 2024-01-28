package Main;

import Controllers.Controller_Scene1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(FXMLLoader.load(Controller_Scene1.class.getResource("/Main/scene1.fxml")), 750, 650);

        stage.setTitle("Housing Loan Calculator");
        stage.setScene(scene);
        stage.show();

    }
}























