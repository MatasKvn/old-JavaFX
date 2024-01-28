package main;

import java.io.File;

public class PersonalFile {
    public PersonalFile(String fileName){
        this.fileName = fileName;
    }
    private String fileName;
    private File file;

    public String getFileName(){
        return fileName;
    }
}
