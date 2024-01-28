package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Scene1Controller implements Initializable {

    private String inputFileName = "MOCK_DATA.csv";

    @FXML private AnchorPane pane;
    @FXML private TableView table;

    @FXML private TableColumn first_name;
    private TextField first_nameFilter = new TextField();

    @FXML private TableColumn last_name;
    private TextField last_nameFilter = new TextField();

    @FXML private TableColumn email;
    private TextField emailFilter = new TextField();

    @FXML private TableColumn imagelink;
    private TextField imagelinkFilter = new TextField();

    @FXML private TableColumn ip_address;
    private TextField ip_addressFilter = new TextField();


    @FXML private Label numberOfEntries;



    private ObservableList<Person> data;

    Map<String, List<Person>> personMap;
    public void onCreateMap() throws IOException {
        if(personMap != null){
            personMap.get(keyInput.getText());
            return;
        }

        ObservableList<Person> dat = table.getItems();

//        personMap = Arrays.stream(dat.stream().filter(person -> {
//            if(Integer.parseInt(person.getIpFirstDigits()) == Integer.parseInt(keyInput.getText())){
//                return true;
//            }
//            return false;
//                })
//                .collect(Collectors.toMap(Person::getIpFirstDigits, ));

        personMap = dat.stream().filter(person -> {
            if(Integer.parseInt(person.getIpFirstDigits()) == Integer.parseInt(keyInput.getText())){
                return true;
            }
            return false;
        }).collect(Collectors.groupingBy(Person::getIpFirstDigits));


//        personMap = new HashMap<>();
//        dat.stream().filter(d->d.getIpFirstDigits().contains("12"))
//
//        for(int i=0; i<dat.size(); ++i){
//            ArrayList<Person> temp = new ArrayList();
//            for(int j=0; j<dat.size(); ++j){
//                if(Integer.parseInt(dat.get(i).getIpFirstDigits()) == Integer.parseInt(dat.get(j).getIpFirstDigits())){
//                    temp.add(dat.get(j));
////                    System.out.println(dat.get(i) + " " + dat.get(j));
//                }
//            }
////            System.out.println(temp);
//            personMap.put(dat.get(i).getIpFirstDigits(), temp);
//        }

//        dat.stream().forEach(person -> {
//
//            synchronized (person){
//                ArrayList<Person> temp = new ArrayList();
//                temp.stream().forEach(person1 -> {
//                    synchronized (person1){
//                        if(Integer.parseInt(person.getIpFirstDigits()) == Integer.parseInt(person1.getIpFirstDigits())){
//                            temp.add(person1);
//                        }
//                    }
//
//                        });
////                for(int j=0; j<dat.size(); ++j){
////                    if(Integer.parseInt(person.getIpFirstDigits()) == Integer.parseInt(dat.get(j).getIpFirstDigits())){
////                        temp.add(dat.get(j));
//////                    System.out.println(dat.get(i) + " " + dat.get(j));
////                    }
////                }
//                personMap.put(person.getIpFirstDigits(), temp);
//            }
//
//        });



//        ip_address.setCellValueFactory(new PropertyValueFactory<Person, String>("private String ipFirstDigits;"));

    }

    public void onMapToTable(){
        if(keyInput.getText().isBlank()){
            table.setItems(data);
            return;
        }

        ObservableList<Person> dat = table.getItems();


        ObservableList<Person> mapItems = dat.stream()
                .filter(person ->  {
                    if(Integer.parseInt(person.getIpFirstDigits()) == Integer.parseInt(keyInput.getText())
                    ){
                        System.out.println(person);
                        return true;
                    }else {
                        return false;
                    }


                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        System.out.println(mapItems);
//        for (int i=0; i<data.size(); ++i){
//            if(data.get(i).getIpFirstDigits().equals(keyInput.getText()))
//        }

        table.setItems(mapItems);
        numberOfEntries.setText(Integer.toString(table.getItems().size()));

    }

    @FXML private TextField keyInput;
    @FXML private TextField mapValue;

    public void onGetFromMap(){
        if(personMap == null){
            mapValue.setText("Map does not exist!");
            return;
        }

//        System.out.println(personMap.get("107"));
        if(personMap.containsKey(keyInput.getText())){
            mapValue.setText(personMap.get(keyInput.getText()).toString());
//            System.out.print(personMap.get(keyInput.getText()).toString());
        }else {
            mapValue.setText("Key does not exitst!");
        }
    }


    public void onFirst_nameChangeCase(){
        ObservableList<Person> dat = table.getItems();
        if(Character.isUpperCase(dat.get(0).getFirst_name().charAt(0))){
            for (int i=0; i<table.getItems().size(); ++i){
                dat.get(i).setFirst_name(dat.get(i).getFirst_name().toLowerCase());
            }
        }else {
            for (int i=0; i<table.getItems().size(); ++i){
                dat.get(i).setFirst_name(dat.get(i).getFirst_name().toUpperCase());
            }
        }
        table.refresh();
    }

    public void onLast_nameChangeCase(){
        ObservableList<Person> dat = table.getItems();
        if(Character.isUpperCase(dat.get(0).getLast_name().charAt(0))){
            for (int i=0; i<table.getItems().size(); ++i){
                dat.get(i).setLast_name(dat.get(i).getLast_name().toLowerCase());
            }
        }else {
            for (int i=0; i<table.getItems().size(); ++i){
                dat.get(i).setLast_name(dat.get(i).getLast_name().toUpperCase());
            }
        }
        table.refresh();
    }


    public void onSortDown(){
        ObservableList<Person> data = table.getItems();

        Comparator<Person>  comparator = Comparator.comparing(Person::getFirst_name).reversed();
        ObservableList<Person> sortedList = data.stream().sorted(comparator).collect(Collectors.toCollection(FXCollections::observableArrayList));
        table.setItems(sortedList);
    }
    public void onSortUp(){
        ObservableList<Person> data = table.getItems();

        Comparator<Person>  comparator = Comparator.comparing(Person::getFirst_name);
        ObservableList<Person> sortedList = data.stream().sorted(comparator).collect(Collectors.toCollection(FXCollections::observableArrayList));
        table.setItems(sortedList);
    }

    public void onFilter(){
        table.setItems(data);

        // sort by name
//        Comparator<Person>  comparator = Comparator.comparing(Person::getFirst_name);
//        ObservableList<Person> sortedList = data.stream().sorted(comparator).collect(Collectors.toCollection(FXCollections::observableArrayList));
//        table.setItems(sortedList);

        numberOfEntries.setText(Integer.toString(table.getItems().size()));
        if(first_nameFilter.getText().isBlank() && last_nameFilter.getText().isBlank() && emailFilter.getText().isBlank() &&
                imagelinkFilter.getText().isBlank() && ip_addressFilter.getText().isBlank()){

            return;
        }

        ObservableList<Person> filteredList = data.stream()
                .filter(person ->  {
                    if(person.getFirst_name().contains(first_nameFilter.getText()) &&
                    person.getLast_name().contains(last_nameFilter.getText()) &&
                    person.getEmail().contains(emailFilter.getText()) &&
                    person.getImagelink().contains(imagelinkFilter.getText()) &&
                    person.getIp_address().contains(ip_addressFilter.getText())
                    ){
                        return true;
                    }else {
                        return false;
                    }


                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        table.setItems(filteredList);
        numberOfEntries.setText(Integer.toString(table.getItems().size()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        double pos = table.getLayoutX();
        first_nameFilter.setLayoutX(pos);
        first_nameFilter.setPrefWidth(first_name.getWidth());
        pos += first_name.getWidth();
        first_nameFilter.setPromptText("filter:");
        first_nameFilter.setOnKeyReleased(event -> onFilter());


        last_nameFilter.setLayoutX(pos);
        last_nameFilter.setPrefWidth(last_name.getWidth());
        pos += last_name.getWidth();
        last_nameFilter.setPromptText("filter:");
        last_nameFilter.setOnKeyReleased(event -> onFilter());

        emailFilter.setLayoutX(pos);
        emailFilter.setPrefWidth(email.getWidth());
        pos += email.getWidth();
        emailFilter.setPromptText("filter:");
        emailFilter.setOnKeyReleased(event -> onFilter());

        imagelinkFilter.setLayoutX(pos);
        imagelinkFilter.setPrefWidth(imagelink.getWidth());
        pos += imagelink.getWidth();
        imagelinkFilter.setPromptText("filter:");
        imagelinkFilter.setOnKeyReleased(event -> onFilter());

        ip_addressFilter.setLayoutX(pos);
        ip_addressFilter.setPrefWidth(ip_address.getWidth());
        ip_addressFilter.setPromptText("filter:");
        ip_addressFilter.setOnKeyReleased(event -> onFilter());

        pane.getChildren().addAll(first_nameFilter, last_nameFilter, emailFilter, imagelinkFilter, ip_addressFilter);

        first_name.setCellValueFactory(new PropertyValueFactory<Person, String>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<Person, String>("last_name"));
        email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        imagelink.setCellValueFactory(new PropertyValueFactory<Person, String>("imagelink"));
        ip_address.setCellValueFactory(new PropertyValueFactory<Person, String>("ip_address"));

        data = FXCollections.observableArrayList();
        table.setItems(data);


        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/main/" + inputFileName));
            String line = reader.readLine();
            do {
                line = reader.readLine();
                if(line == null) break;
                Person p1 = new Person(line);
                data.add(p1);
//                System.out.println(p1.toString());

            } while (line != null);

            numberOfEntries.setText(Integer.toString(table.getItems().size()));



//            // Sort by First_name
//            Comparator<Person>  comparator = Comparator.comparing(Person::getFirst_name);
//            ObservableList<Person> sortedList = data.stream().sorted(comparator).collect(Collectors.toCollection(FXCollections::observableArrayList));
//            table.setItems(sortedList);





        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
