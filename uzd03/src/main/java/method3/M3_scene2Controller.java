package method3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.UserBMI;

import java.io.IOException;

public class M3_scene2Controller extends Node {

    private static Stage stage;
    private Scene scene;
    @FXML
    private Label name;
    @FXML
    private Label age;
    @FXML
    private Label height;
    @FXML
    private Label weight;
    @FXML
    private Label BMI;
    @FXML
    private Label BMICategory;

    UserBMI user;
    public void receiveData(ActionEvent event){

        UserBMIHolder holder = UserBMIHolder.getInstance();
        user = holder.getUserBMI();
        displayData();
    }

    public void displayData(){
        name.setText("Name: " + user.getName());
        age.setText("Age: " + Integer.toString(user.getAge()));
        height.setText("Height: " + Double.toString(user.getHeight()));
        weight.setText("Weight: " + Double.toString(user.getWeight()));

        user.calculateBMI();
        user.calculateBMICategory();
        BMI.setText("BMI: " + Double.toString(user.getBMI()));
        BMICategory.setText("Category: " + user.getBMICategory());

        stage.setTitle(user.getName() + "'s BMI");
    }

}
