package Controllers;

import Calculators.Payment;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Scene2 implements Initializable {


    private LineChart<?, ?> chart;
    private static ArrayList<Payment> lList = new ArrayList<>();
    private static ArrayList<Payment> aList = new ArrayList<>();

    public static void getData(ArrayList<Payment> linear, ArrayList<Payment> annuity) {
        lList = linear;
        aList = annuity;
    }




    public static void openWindow() {
        try {
            Scene scene = new Scene(FXMLLoader.load(Controller_Scene2.class.getResource("scene2.fxml")), 600, 500);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Line Chart");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadChart() {
        getData(lList, aList);

        XYChart.Series linearSeries = new XYChart.Series();
        XYChart.Series annuitySeries = new XYChart.Series();
        for(int i=0; i<lList.size(); ++i){
            System.out.println(lList.get(i).getMonth());
            linearSeries.getData().add(new XYChart.Data(Integer.toString(lList.get(i).getMonth()), lList.get(i).getPayment()));
            annuitySeries.getData().add(new XYChart.Data(Integer.toString(aList.get(i).getMonth()), aList.get(i).getPayment()));
        }
        chart.getData().addAll(linearSeries, annuitySeries);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
    }
}
