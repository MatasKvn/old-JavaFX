package controllers;

import classes.Student;
import classes.StudentTable;
import interfaces.Openable;
import interfaces.Savable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import org.apache.pdfbox.pdmodel.font.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
public class Scene1Controller implements Initializable, Savable, Openable {
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputSurname;
    @FXML
    private TextField inputGroup;

    @FXML private DatePicker datePicker;
    @FXML private CheckBox setAttendanceToWholeGroup;

    private boolean filterDate = true;
    @FXML private DatePicker filterStart;
    @FXML private DatePicker filterEnd;


    private String name;
    private String surname;
    private String group;
    private ObservableList<Student> students = FXCollections.observableArrayList();


    @FXML
    @Override
    public void onSaveToPDF(ActionEvent event){
        // Choose a PDF file to write data to
        FileChooser fileChooser = new FileChooser();
        String cur = System.getProperty("user.dir");
        fileChooser.setInitialDirectory(new File(cur));
        File file = fileChooser.showSaveDialog(new Stage());

        if(file == null){
            return;
        }

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try {
            PDPageContentStream writer = new PDPageContentStream(document, page);
            writer.setFont(PDType1Font.TIMES_ROMAN, 12);

            ObservableList<Student> tempList = table.getItems();

            float y = 700;
            for (int i=0; i<tempList.size(); ++i){
                writer.beginText();
                writer.newLineAtOffset(20, y);
                writer.showText(tempList.get(i).getName());
                writer.showText(" " + tempList.get(i).getSurname());
                writer.showText(" " + tempList.get(i).getGroup());
                writer.endText();
                for (int j=0; j<tempList.get(i).getDates().size(); ++j){
                    y-=12;
                    writer.beginText();
                    writer.newLineAtOffset(45, y);
                    writer.showText(tempList.get(i).getDates().get(j).toString());
                    writer.endText();
                }
                y-=20;
            }

            writer.close();

            document.save(file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    @Override
    public void onOpenExcelFile(ActionEvent event){
        // Choose an excel file to get data from (Has to follow the same format as saved files)
        FileChooser fileChooser = new FileChooser();
        String cur = System.getProperty("user.dir");
        fileChooser.setInitialDirectory(new File(cur));
        Stage stage = new Stage();
        stage.setTitle("Choose a file to open");
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        try {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet1 = workbook.getSheetAt(0);

            System.out.println();

            ObservableList<Student> studentsInput = FXCollections.observableArrayList();
            for (int i=1; i<=sheet1.getLastRowNum(); ++i){
                Row row = sheet1.getRow(i);
                Student newStudent = new Student(row.getCell(0).toString(), row.getCell(1).toString(), row.getCell(2).toString());
                for(int j=3; j<row.getLastCellNum(); ++j){
                    Cell cell = row.getCell(j);
                    LocalDate date = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    newStudent.addDate(date);
                }
                studentsInput.add(newStudent);
            }
            students = studentsInput;
            table.setItems(students);
            table.refresh();
            workbook.close();

        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    @Override
    public void onSaveToExcel(ActionEvent event){
        // Choose an excel file to save data to
        FileChooser fileChooser = new FileChooser();
        String cur = System.getProperty("user.dir");
        fileChooser.setInitialDirectory(new File(cur));
        Stage stage = new Stage();
        stage.setTitle("Choose a file");
        File file = fileChooser.showSaveDialog(stage);

        if(file == null){
            return;
        }

        // Write data to excel file
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");
            sheet.setColumnWidth(0, 256*12);
            sheet.setColumnWidth(1, 256*12);
            sheet.setColumnWidth(2, 256*12);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Surname");
            headerRow.createCell(2).setCellValue("Group");
            headerRow.createCell(3).setCellValue("Attendance");

            ObservableList<Student> tempList = table.getItems();

            int maxDates = 0;
            for (int i=0; i<tempList.size(); ++i){
                if(tempList.get(i).getDates().size() > maxDates){
                    maxDates = tempList.get(i).getDates().size();
                }
            }

            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));

            for (int i=0; i<tempList.size(); ++i){
                Row tempRow = sheet.createRow(i+1);
                tempRow.createCell(0).setCellValue(tempList.get(i).getName());
                tempRow.createCell(1).setCellValue(tempList.get(i).getSurname());
                tempRow.createCell(2).setCellValue(tempList.get(i).getGroup());
                for (int j=3; j<tempList.get(i).getDates().size()+3; ++j){
                    sheet.setColumnWidth(j, 256*12);
                    tempRow.createCell(j).setCellValue(tempList.get(i).getDates().get(j-3));
                    tempRow.getCell(j).setCellStyle(cellStyle);
                }
            }
            if(maxDates >= 1) {
                CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 3, 3+maxDates+((maxDates>=2) ? -1 : 0));
                sheet.addMergedRegion(mergedRegion);
            }

            try {
                FileOutputStream output = new FileOutputStream(file.getName()); //  + ".xlsx"
                workbook.write(output);
                output.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }




    @FXML
    public void onFilter(ActionEvent event){
        ObservableList<Student> studentsAlt = FXCollections.observableArrayList();
        filterDate = !filterDate;

        // Reset original data if filter is off
        if(filterDate){
            table.setItems(students);
            table.refresh();
            return;
        }

        // Check if both filter fields have value
        LocalDate fStart = filterStart.getValue();
        LocalDate fEnd = filterEnd.getValue();
        if(fStart == null || fEnd == null){
            return;
        }

        // Clone the table list to display the filter
        for (int i=0; i<students.size(); ++i) {
            Student clonedStudent = new Student(students.get(i).getName(), students.get(i).getSurname(),students.get(i).getGroup() );
            ArrayList<LocalDate> clonedDates = new ArrayList<>(students.get(i).getDates());
            clonedStudent.setDates(clonedDates);
            studentsAlt.add(clonedStudent);
        }

        // Go through the list of students and remove objects that do not fall into the filter interval
        for(int i=0; i<studentsAlt.size(); ++i){
            for(int j=0; j<studentsAlt.get(i).getDates().size(); ++j){
                LocalDate cur = studentsAlt.get(i).getDates().get(j);
                if((cur.isAfter(fStart) || cur.equals(fStart)) && (cur.isBefore(fEnd) || cur.equals(fEnd))){
//                    System.out.println("ok");
                }else {
//                    studentsAlt.get(i).removeDate(studentsAlt.get(j).getDates().get(j));
                    studentsAlt.get(i).removeDate(cur);
                    --j;
                }
            }
            studentsAlt.get(i).setDatesString();
            if(studentsAlt.get(i).getDates().size() == 0){
                studentsAlt.remove(i);
                --i;
            }
        }

        // Set the items of the table
        table.setItems(studentsAlt);
        table.refresh();
    }

    @FXML
    public void onAddStudent(ActionEvent event) {
        name = inputName.getText();
        surname = inputSurname.getText();
        group = inputGroup.getText();

        // add student data if not empty
        if(name == "" || surname == "" || group == ""){
            return;
        }
        students.add(new Student(name, surname, group));
    }

    @FXML
    public void onEditStudent(ActionEvent event){
        name = inputName.getText();
        surname = inputSurname.getText();
        group = inputGroup.getText();

        // Change student data if fields are not black
        Student tempStudent = (Student) table.getSelectionModel().getSelectedItem();
        if(tempStudent != null){
            if(!name.isBlank()) tempStudent.setName(name);
            if(!surname.isBlank()) tempStudent.setSurname(surname);
            if(!group.isBlank()) tempStudent.setGroup(group);
        }
        table.refresh();
    }

    @FXML
    public void onDeleteStudent(ActionEvent event){
        // Get selected student and remove from list
        Student tempStudent = (Student) table.getSelectionModel().getSelectedItem();
        if(tempStudent != null){
            students.remove(tempStudent);
        }
        table.refresh();
    }

    @FXML
    public void onAddAttendance(ActionEvent event){
        // Get selected student
        Student tempStudent = (Student) table.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();

        if(date != null && tempStudent != null){
            ObservableList<Student> tempList = table.getItems();
            String group = tempStudent.getGroup();

            // Add attendance to the selected group
            if(setAttendanceToWholeGroup.isSelected()){
                for (int i=0; i<tempList.size(); ++i){
                    if(tempList.get(i).getGroup().contentEquals(group)){
                        tempList.get(i).addDate(date);
                    }
                }
            }else {
                tempStudent.addDate(date);
            }

//            System.out.println(tempStudent.getName() + tempStudent.getGroup());
//            for(int i=0; i<tempStudent.getDates().size(); ++i){
//                System.out.println(" " + tempStudent.getDates().get(i));
//            }
//            System.out.println(tempStudent.getDateStr());
        }
        table.refresh();
    }

    @FXML
    public void onRemoveAttendance(ActionEvent event){
        // Get selected student
        Student tempStudent = (Student) table.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();

        if(date != null && tempStudent != null){
            ObservableList<Student> tempList = table.getItems();
            String group = tempStudent.getGroup();

            // Removes attendance from the selected group
            if(setAttendanceToWholeGroup.isSelected()){
                for (int i=0; i<tempList.size(); ++i){
                    if(tempList.get(i).getGroup().contentEquals(group)){
                        tempList.get(i).removeDate(date);
                    }
                }
            }else {
                tempStudent.removeDate(date);
            }
//            System.out.println(tempStudent.getName() + tempStudent.getGroup());
//            for(int i=0; i<tempStudent.getDates().size(); ++i){
//                System.out.println(" " + tempStudent.getDates().get(i));
//            }
//            System.out.println(tempStudent.getDateStr());
        }
        table.refresh();
    }


    private StudentTable table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the table, it's size, it's items and add it to the scene
        table = new StudentTable(students);
        table.setLayout(10, 90, 580, 400);
        table.setColumnWidths(100, 120, 45, 300);

        pane.getChildren().add(table);
    }

}
