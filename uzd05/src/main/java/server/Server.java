package server;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import singletons.ServerSocketHolder;

import java.io.*;
import java.lang.ref.Cleaner;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private BufferedWriter bufferedWriter;

    private TextArea display;

    public Server(ServerSocket serverSocket, TextArea display){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File("src/main/resources/logs/log.txt")));
            bufferedWriter.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.display = display;
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try{
            while (!serverSocket.isClosed()){
                System.out.println("Server started!");
//                break;

                Socket socket = serverSocket.accept();
//                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket, bufferedWriter, display);
                Thread thread = new Thread(clientHandler);
                thread.start();


            }

        } catch (IOException e){
            closeServerSocket();
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket, new TextArea());
            server.startServer();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
