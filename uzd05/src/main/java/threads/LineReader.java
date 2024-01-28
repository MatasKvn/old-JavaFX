package threads;

import java.io.*;
import java.security.spec.ECField;
import java.util.concurrent.ExecutionException;

public class LineReader implements Runnable {

    private File file;
    private BufferedReader reader;
    public LineReader(File file){
        this.file = file;

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            String line = reader.readLine();
            line = reader.readLine();
//            line = reader.readLine();
//            String[] parts = line.split(";");
//            String fileName = "";
//            int fileLength = 0;
//            fileName += parts[0].substring(0, 3);
//            fileName += parts[1].substring(0, 3);
//            fileName += parts[4].substring(parts[4].length()-3, parts[4].length());
//            for (int i=0; i<parts.length; ++i){
//                System.out.println(parts[i]);
//            }
//            System.out.println(" " + fileName);
            while (line != null){
                line = reader.readLine();
                System.out.println(line);
                String[] parts = line.split(";");
                String fileName = "";
                int fileLength = 0;
//                System.out.println(parts[0]);
                try {
                    fileName += parts[0].substring(0, 3);
                    fileName += parts[1].substring(0, 3);
                    fileName += parts[4].substring(parts[4].lastIndexOf(".")+1, parts[4].length());
                }catch (Exception e){
                    System.out.println("Retarded file");
                    break;
                }

                System.out.println(fileName);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
