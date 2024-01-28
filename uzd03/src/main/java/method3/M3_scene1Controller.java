package method3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.UserBMI;
import method1.M1_scene2Controller;

import java.io.IOException;

public class M3_scene1Controller {

    private Stage stage;
    private Scene scene;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/method3/scene2.fxml"));
        scene = new Scene(loader.load());
        stage.close();
        stage.setScene(scene);
        stage.show();
        UserBMIHolder holder = UserBMIHolder.getInstance();
        holder.setUserBMI(user);


    }
}
