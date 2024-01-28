package threads;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import main.PersonalData;
import main.PersonalFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileEraser implements Runnable{
    private TableView t1;
    private TableView t2;
    private boolean deleteAll;

    public FileEraser(TableView t1, TableView t2, boolean deleteAll){
        this.t1 = t1;
        this.t2 = t2;
        this.deleteAll = deleteAll;
    }

    // Removes one(all if deleteAll == true) file from the 2nd table and deletes the file
    @Override
    public void run() {
        if(deleteAll){
            deleteAll();
        }else {
            deleteSingle();
        }
        t1.refresh();
        t2.refresh();
    }

    private void deleteAll() {
        ObservableList<PersonalFile> files = t2.getItems();
        List<PersonalData> addedItems = new ArrayList<>();

        List<Thread> threads = new ArrayList<>();

        for (PersonalFile file : files) {
            Thread thread = new Thread(() -> {
                try {
                    // Read the file
                    BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/data/" + file.getFileName()));
                    String line = fileReader.readLine();
                    fileReader.close();

                    // Create PersonalData object from the file content
                    PersonalData temp = new PersonalData(line);

                    // Add the item to the list of added items
                    synchronized (addedItems) {
                        addedItems.add(temp);
                    }

                    // Delete the file
                    File f = new File("src/main/resources/data/" + file.getFileName());
                    f.delete();
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

        // Update the tables
        Platform.runLater(() -> {
            synchronized (files) {
                t1.getItems().addAll(addedItems);
            }
            files.removeAll(files);
        });
    }
//    private void deleteAll(){
//        ObservableList<PersonalFile> files = t2.getItems();
//
//        files.parallelStream().forEach(file -> {
//            BufferedReader fileReader = null;
//            try {
//                fileReader = new BufferedReader(new FileReader("src/main/resources/data/" + file.getFileName()));
//                String s = fileReader.readLine();
//                fileReader.close();
//                PersonalData temp = new PersonalData(s);
//
//                synchronized (files){
//                    t1.getItems().add(temp);
//                }
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            File f = new File("src/main/resources/data/" + file.getFileName());
//            f.delete();
//
//        });
//        files.removeAll(files);
//    }

    private void deleteSingle(){
        PersonalFile dat = (PersonalFile) t2.getSelectionModel().getSelectedItem();
        if(dat != null){
            ObservableList<PersonalFile> files = t2.getItems();

            // Read file and add content to 1st table
            try {
                BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/data/" + dat.getFileName()));
                String s = fileReader.readLine();
                fileReader.close();
                PersonalData temp = new PersonalData(s);
                t1.getItems().add(temp);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            File file = new File("src/main/resources/data/" + dat.getFileName());
            file.delete();
            files.remove(dat);
        }
    }
}
