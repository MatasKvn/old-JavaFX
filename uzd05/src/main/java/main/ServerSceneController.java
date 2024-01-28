package main;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.ServerThread;
import singletons.ServerSocketHolder;

import java.io.*;

public class ServerSceneController {
    @FXML private TextField portField;
    @FXML private TextField input;
    @FXML private TextArea displayArea;
    private boolean serverIsActive = false;


    public void start(){
        if(serverIsActive == true){
            displayArea.setText("Server already running!");
            return;
        }

        try {
            int port = Integer.parseInt(portField.getText());
            System.out.println(portField.getText());

            ServerThread s = new ServerThread(port, displayArea);
            Thread serverThread = new Thread(s);
            serverThread.start();
            serverIsActive = true;
            displayArea.setText("Server is now running!");
        }catch (Exception e){
            e.printStackTrace();
            displayArea.setText("Unable to start server!");
        }
    }

    public void stop(){
        System.exit(0);
        try {
            ServerSocketHolder.getInstance().getServerSocket().close();
            serverIsActive = false;
            displayArea.setText("Server closed!");
        }catch (NullPointerException e){
            displayArea.setText("Server is not running!");

        } catch (IOException e) {
            e.printStackTrace();
            displayArea.setText("Unable to close server!");
        }
    }


}
