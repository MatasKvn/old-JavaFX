package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.Client;
import server.ClientThread;
import server.ServerThread;
import singletons.ClientHolder;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientSceneController implements Initializable {

    @FXML private TextField portField;
    @FXML private TextField ipField;
    @FXML private TextField usernameField;
    @FXML private TextField input;
    @FXML private TextArea displayArea;

    private boolean joined = false;



    public void sendMessage(){

        ClientHolder.getInstance().setSendMessage(true);
    }
    private ClientThread c;

    public void join(){
        if(joined == true){
            return;
        }

        try {
            int port = Integer.parseInt(portField.getText());
            String ip = ipField.getText();
            String username = usernameField.getText();
            c = new ClientThread(port, ip, username, displayArea);
            Thread clientThread = new Thread(c);
            clientThread.start();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Unable to connect to server!");
        }
    }



    //does not work
    public void leave(){
        System.exit(0);
//        try {
//            ServerSocketHolder.getInstance().getServerSocket().close();
//            serverIsActive = false;
//            displayArea.setText("Server closed!");
//        }catch (NullPointerException e){
//            displayArea.setText("Server is not running!");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            displayArea.setText("Unable to close server!");
//        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientHolder.getInstance().setTextField(this.input);
    }
}
