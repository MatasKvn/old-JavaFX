package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PersonalData {

    private String fileName;
    private File file;

    public PersonalData(String line){
        this.line = line;
    }
    public String getLine() {
        return line;
    }

    String line;

    public void setLine(String line) {
        this.line = line;
    }

    public String getFileName(){
        if(fileName != null) return fileName;
        String[] parts = line.split(";");
        fileName = "";
        int fileLength = 0;
        for(int i=0; i<3 && i<parts[0].length(); ++i){fileName += parts[0].charAt(i);
        }
        for(int i=0; i<3 && i<parts[1].length(); ++i){
            fileName += parts[1].charAt(i);
        }
        fileName += parts[4].substring(parts[4].lastIndexOf(".")+1, parts[4].length());
        fileName += ".txt";
//        System.out.println(fileName);
//        column.setCellValueFactory(new PropertyValueFactory<String, String>(lines.toString()));

        return fileName;
    }

}
