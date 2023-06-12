package method2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.UserBMI;
import method1.M1_scene2Controller;

import java.io.IOException;

public class M2_scene1Controller {

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/method2/scene2.fxml"));


        M2_scene2Controller scene2Controller = new M2_scene2Controller();
        scene2Controller.setUser(user);
        loader.setController(scene2Controller);



        Stage s = new Stage();
        Parent root = loader.load();
        scene = new Scene(root);

        stage.close();
        s.setScene(scene);
        s.show();
    }

}
