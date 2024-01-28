package threads;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.PersonalData;

import java.io.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LineReader implements Runnable {

    private File file;
    private BufferedReader reader;
    private TableView table;
    private TableColumn column;
    public LineReader(File file, TableColumn column, TableView table){
        this.file = file;
        this.column = column;
        this.table = table;

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Reads MOCK_DATA.csv and puts each lien into the objects in the table
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            ObservableList lines = table.getItems();

            // read the first line "first_name;last_name;email;imagelink;ip_address"
            reader.readLine();

            // read all lines and add them to the table list
            do {
                line = reader.readLine();
                if(line == null) break;
                System.out.println(line);
                PersonalData dat = new PersonalData(line);
                lines.add(dat);

            } while (line != null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
