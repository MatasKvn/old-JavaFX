package threads;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.PersonalData;
import main.PersonalFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileCreator implements Runnable {

    private TableView t1;
    private TableView t2;
    private boolean toFileAll;

    // Constructor for single file creation
    public FileCreator(TableView t1, TableView t2, boolean toFileAll){
        this.t1 = t1;
        this.t2 = t2;
        this.toFileAll = toFileAll;
    }

    // Removes one(all if toFileAll == true) file from the 1st table and adds it to the 2nd
    @Override
    public void run() {
        if(toFileAll){
            createAll();
        }else {
            createSingle();
        }
        t1.refresh();
        t2.refresh();
    }


    private void createAll() {
        ObservableList<PersonalData> lines = t1.getItems();

        ArrayList<Thread> threads = new ArrayList<>();

        for (PersonalData line : lines) {
            Thread thread = new Thread(() -> {
                try {
                    // Create the file
                    FileWriter fileWriter = new FileWriter("src/main/resources/data/" + line.getFileName());
                    fileWriter.write(line.getLine());
                    fileWriter.close();

                    // Add file to table2
                    ObservableList<PersonalFile> files = t2.getItems();
                    synchronized (files) {
                        files.add(new PersonalFile(line.getFileName()));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Start the thread
            thread.start();
            threads.add(thread);
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Remove entries from table1
        Platform.runLater(() -> {
            synchronized (lines) {
                lines.clear();
            }
        });
    }

//private void createAll() {
//    ObservableList<PersonalData> lines = t1.getItems();
//
//    lines.parallelStream().forEach(line -> {
//        try {
//            // Create the file
//            FileWriter fileWriter = new FileWriter("src/main/resources/data/" + line.getFileName());
//            fileWriter.write(line.getLine());
//            fileWriter.close();
//
//            // Add the file to the second table
//            ObservableList<PersonalFile> files = t2.getItems();
//            synchronized (files) {
//                files.add(new PersonalFile(line.getFileName()));
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    });
//
//    // Remove the entries from the first table
//        lines.removeAll(lines);
//}



    private void createSingle(){
        PersonalData dat = (PersonalData) t1.getSelectionModel().getSelectedItem();

        if(dat != null){
            FileWriter fileWriter = null;
            try {
                t1.getItems().remove(dat);
                fileWriter = new FileWriter("src/main/resources/data/" + dat.getFileName());
                fileWriter.write(dat.getLine());
                fileWriter.close();

                ObservableList<PersonalFile> files = t2.getItems();
                PersonalFile file = new PersonalFile(dat.getFileName());
                files.add(file);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
