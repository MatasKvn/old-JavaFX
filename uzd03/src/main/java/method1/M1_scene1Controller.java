package method1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.UserBMI;

import java.io.IOException;
import java.util.Objects;

public class M1_scene1Controller extends Node {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField input_age;
    @FXML
    private TextField input_name;
    @FXML
    private TextField input_weight;
    @FXML
    private TextField input_height;


    public void sendData(ActionEvent event) throws IOException{
        int age = Integer.parseInt(input_age.getText());
        int height = Integer.parseInt(input_height.getText());
        int weight = Integer.parseInt(input_weight.getText());
        UserBMI user = new UserBMI(input_name.getText(), age, height,weight);

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setUserData(user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/scene2.fxml"));
        scene = new Scene(loader.load());
        stage.close();
        stage.setScene(scene);
        stage.show();
    }
}
