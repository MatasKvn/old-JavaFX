package classes;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class StudentTable extends CustomTableComponent{

    public void settName(TableColumn tName) {
        this.tName = tName;
    }

    public void settSurname(TableColumn tSurname) {
        this.tSurname = tSurname;
    }

    public void settGroup(TableColumn tGroup) {
        this.tGroup = tGroup;
    }

    private TableColumn tName;
    private TableColumn tSurname;
    private TableColumn tGroup;
    private TableColumn tAttendance;


    public StudentTable(ObservableList<?> students){
        super(students);
        tName = new TableColumn<>("Name");
        tSurname = new TableColumn<>("Surname");
        tGroup = new TableColumn<>("Group");
        tAttendance = new TableColumn<>("Attendance");
        createColumns();
    }

    private ArrayList<LocalDate> dates = new ArrayList<>();
    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }
    public ArrayList<LocalDate> getDates() {
        return dates;
    }
    public void addDate(LocalDate date){
        dates.add(date);
    }


    @Override
    public void createColumns() {
        tName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        tSurname.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        tGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        tAttendance.setCellValueFactory(new PropertyValueFactory<Student, String>("dateStr"));


        this.getColumns().addAll(tName, tSurname, tGroup, tAttendance);
    }

    public void setColumnWidths(double col1, double col2, double col3, double col4){
        tName.setPrefWidth(col1);
        tSurname.setPrefWidth(col2);
        tGroup.setPrefWidth(col3);
        tAttendance.setPrefWidth(col4);
    }

}
