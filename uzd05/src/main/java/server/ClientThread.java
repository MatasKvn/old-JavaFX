package server;

import javafx.scene.control.TextArea;
import singletons.ClientHolder;

import java.io.IOException;
import java.net.Socket;

public class ClientThread implements Runnable{

    private int port;
    private String ip;
    private String username;
    private TextArea display;
    public ClientThread(int port, String ip, String username, TextArea display){
        this.port = port;
        this.ip = ip;
        this.username = username;
        this.display = display;
    }


    private Client client;

    @Override
    public void run() {

        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            client = new Client(socket, username, display);
            client.listenForMessage();
            client.sendMessage();
        } catch (IOException e) {
            System.out.println("Unable to connect to server!");
            e.printStackTrace();
        }
//        System.out.println("Successfully connected to chat!");



    }
}
