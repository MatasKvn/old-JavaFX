package server;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import singletons.ServerSocketHolder;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread implements Runnable{

    private int port;
    private TextArea display;
    public ServerThread(int port, TextArea display){
        this.port = port;
        this.display = display;
    }


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            ServerSocketHolder.getInstance().setServerSocket(serverSocket);
            Server server = new Server(serverSocket, display);
            server.startServer();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
