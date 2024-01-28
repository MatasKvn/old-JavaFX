package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import threads.FileCreator;
import threads.FileEraser;
import threads.LineReader;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/*
Duomenys:
id, first_name, last_name, email, imagelink, ip_address
Saugoti i faila pavadinimu vardo, pavardes pirmos 3 raides, 3 paskutiniai ip skaiciai(po paskutinio tasko)(pvz: matkva999)

Veiksmai:
- Nuskaityt faila, sudet (jį?) lentele
- Vienos eilutės(zmogaus) duomenis saugoti atskirame faile
- Sukurus faila istrinti irasa is lenteles
- Sukurti dar viena lentele(atiluktu darbų sarašas)(realiu laiku atnaujinami duomenys)
- Ištrinti faila ant jo paspaudus atliktu darbu lenteleje
- Darbui išsitrinus, (tai?) matosi kitoje lentelėje, kurioje nuskaitomi įrašai
- Mygtukas ištrinti visus failus

 */
public class Scene1Controller implements Initializable {

    @FXML private TableView table1;
    @FXML private TableColumn table1Column;

    @FXML private TableView table2;
    @FXML private TableColumn table2Column;


    @FXML
    public void onCreateFile(MouseEvent event){
        System.out.println("Creating selected file");
        FileCreator fileCreator = new FileCreator(table1, table2, false);
        Thread temp = new Thread(fileCreator);
        temp.start();
    }
    @FXML
    public void onDeleteFile(MouseEvent event){
        System.out.println("Deleting selected file");
        FileEraser fileEraser = new FileEraser(table1, table2, false);
        Thread temp = new Thread(fileEraser);
        temp.start();
    }

    @FXML
    public void onToFileAll(ActionEvent event){
        System.out.println("Creating all files");
        FileCreator fileCreator = new FileCreator(table1, table2, true);
        Thread temp = new Thread(fileCreator);
        temp.start();
    }
    @FXML
    public void onDeleteAllFiles(ActionEvent event){
        System.out.println("Deleting all files");
        FileEraser fileEraser = new FileEraser(table1, table2, true);
        Thread temp = new Thread(fileEraser);
        temp.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create a list for the first table
        ObservableList<PersonalData> lines = FXCollections.observableArrayList();
        table1Column.setCellValueFactory(new PropertyValueFactory<PersonalData, String>("line"));
        table1.setItems(lines);

        //Create a list for the second table
        ObservableList<PersonalFile> files = FXCollections.observableArrayList();
        table2Column.setCellValueFactory(new PropertyValueFactory<PersonalFile, String>("fileName"));

        // Read MOCK_DATA.csv
        File file = new File("src/main/resources/main/MOCK_DATA.csv");
//        LineReader l1 = new LineReader(file, table1Column, table1);
//        Thread thread1 = new Thread(l1);
//        thread1.start();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";

            // read the first line "first_name;last_name;email;imagelink;ip_address"
            reader.readLine();

            // read all lines and add them to the table list
            do {
                line = reader.readLine();
                if(line == null) break;
//                System.out.println(line);
                PersonalData dat = new PersonalData(line);
                lines.add(dat);

            } while (line != null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

