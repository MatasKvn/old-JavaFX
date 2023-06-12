package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import method1.M1_scene1Controller;
import method2.M2_scene1Controller;
import method3.M3_scene1Controller;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    private Stage stage;

    Parent root;
    Scene scene;

    private void getStage(ActionEvent event){

        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
    }
    public void first(ActionEvent event) throws IOException {
        getStage(event);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/scene1.fxml"));
        root = loader.load();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void second(ActionEvent event) throws IOException{
        getStage(event);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/method2/scene1.fxml"));
        root = loader.load();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void third(ActionEvent event) throws IOException{
        getStage(event);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/method3/scene1.fxml"));
        root = loader.load();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
