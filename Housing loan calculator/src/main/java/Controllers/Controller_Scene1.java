package Controllers;

import Calculators.AnnuityPaymentCalculator;
import Calculators.LinearPaymentCalculator;
import Calculators.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Scene1 implements Initializable {

//    public void openWindow(Stage stage) {
//        try {
//            Scene scene = new Scene(FXMLLoader.load(Controller_Scene1.class.getResource("/Main/scene1.fxml")), 750, 650);
//
//            stage.setTitle("Housing Loan Calculator");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    // input
    @FXML
    private TextField input_sum;
    @FXML
    private TextField input_timeYears;
    @FXML
    private TextField input_timeMonths;
    @FXML
    private ChoiceBox<String> input_graphType = new ChoiceBox<String>();
    @FXML
    private TextField input_interestRate;
    @FXML
    private CheckBox postponeCheck;
    @FXML
    private TextField input_postponeFrom;
    @FXML
    private TextField input_postponeTo;
    private int postponeFrom, postponeTo;
    @FXML
    private CheckBox filterCheck;
    @FXML
    private TextField input_filterFrom;
    @FXML
    private TextField input_filterTo;
    private int filterFrom, filterTo;


    private static double sum;
    private static int period;
    private static double interestRate;


    @FXML
    private TableView<Payment> table;
    @FXML
    private  TableColumn<Payment, Integer> table_months;
    @FXML
    private TableColumn<Payment, Double> table_payment;
    @FXML
    private TableColumn<Payment, Double> table_interest;
    @FXML
    private TableColumn<Payment, Double> table_credit;
    @FXML
    private TableColumn<Payment, Double> table_sumLeft;
    @FXML
    private LineChart<?, ?> chart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        input_graphType.getItems().add("Linear");
        input_graphType.getItems().add("Annuity");
        input_graphType.setValue("Linear");
    }

    ArrayList<Payment> lList = new ArrayList<Payment>();
    ArrayList<Payment> aList = new ArrayList<Payment>();

    public void showChart (ActionEvent event) {
        Controller_Scene2.openWindow();
        Controller_Scene2 temp = new Controller_Scene2();
        temp.loadChart();
    }
    public void submitInput (ActionEvent event) {
        try {
            sum = Double.parseDouble(input_sum.getText());
            period = Integer.parseInt(input_timeYears.getText()) * 12;
            period += Double.parseDouble(input_timeMonths.getText());
            interestRate = Double.parseDouble(input_interestRate.getText());
            System.out.printf("Data:\n Sum: %.2f\n period: %d\n interest: %.2f\n", sum, period, interestRate);
            if(postponeCheck.isSelected()){
//                System.out.println("Postponed");
                postponeFrom = Integer.parseInt(input_postponeFrom.getText());
                postponeTo = Integer.parseInt(input_postponeTo.getText());
            }else {
                postponeFrom = 0;
                postponeTo = 0;
            }

            if(filterCheck.isSelected()) {
//                System.out.println("Filtered");
                filterFrom = Integer.parseInt(input_filterFrom.getText());
                filterTo = Integer.parseInt(input_filterTo.getText());
            }else {
                filterFrom = 0;
                filterTo = 0;
            }
            System.out.println("Pospone from: " + postponeFrom + " to " + postponeTo);
            System.out.println("Filter from: " + filterFrom + " to " + filterTo);;
        }
        catch (NumberFormatException e) {
            System.out.println("Enter only numbers");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if(input_graphType.getValue() == "Linear") {
            System.out.println("Linear type selected");
        }else if(input_graphType.getValue() == "Annuity") {
            System.out.println("Annuity type selected");
        }else {
            System.out.println("Type not selected");
        }



        LinearPaymentCalculator lCalculator = new LinearPaymentCalculator(period, sum, interestRate, postponeFrom, postponeTo, filterFrom, filterTo);
        lList.clear();
        aList.clear();
        lList =  lCalculator.calculate(lList);
        AnnuityPaymentCalculator aCalculator = new AnnuityPaymentCalculator(period, sum, interestRate, postponeFrom, postponeTo, filterFrom, filterTo);
        aList = aCalculator.calculate(aList);
//        System.out.println(xddd.get(3).getPayment());

        ObservableList<Payment> oList = FXCollections.observableArrayList(
                ((input_graphType.getValue() == "Linear") ? lList : aList)
        );
        table.setItems(oList);
        table_months.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("month"));
        table_payment.setCellValueFactory(new PropertyValueFactory<Payment, Double>("payment"));
        table_interest.setCellValueFactory(new PropertyValueFactory<Payment, Double>("interest"));
        table_credit.setCellValueFactory(new PropertyValueFactory<Payment, Double>("credit"));
        table_sumLeft.setCellValueFactory(new PropertyValueFactory<Payment, Double>("sumLeft"));

        chart.getData().clear();

        XYChart.Series linearSeries = new XYChart.Series();
        linearSeries.setName("Linear");
        XYChart.Series annuitySeries = new XYChart.Series();
        annuitySeries.setName("Annuity");
        filterFrom = (filterFrom == 0) ? 0 : filterFrom-1;
        for(int i=0; i<lList.size(); ++i){
//            System.out.println(lList.get(i).getMonth());
            linearSeries.getData().add(new XYChart.Data(Integer.toString(i+1+filterFrom), lList.get(i).getPayment()));
            annuitySeries.getData().add(new XYChart.Data(Integer.toString(i+1+filterFrom), aList.get(i).getPayment()));
        }

        chart.getData().addAll(linearSeries, annuitySeries);
    }

    public void exportToFile() throws IOException {
        FileWriter writer = new FileWriter("/Main/output.txt");

        for(int i=0; i<lList.size(); ++i) {
            writer.write("Month:" + aList.get(i).getMonth() + "\n");
            writer.write(" Monthly payment: " + lList.get(i).getPayment() + "\n");
            writer.write(" Interest: " + lList.get(i).getInterest() + "\n");
            writer.write(" Credit: " + lList.get(i).getCredit() + "\n");
            writer.write(" Leftover sum: " + lList.get(i).getSumLeft() + "\n\n");
        }
        writer.close();
    }






}












