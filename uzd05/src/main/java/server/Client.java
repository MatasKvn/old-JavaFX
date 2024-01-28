package server;

import javafx.scene.control.TextArea;
import singletons.ClientHolder;
import singletons.ServerSocketHolder;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private TextArea display;


    public Client(Socket socket, String username, TextArea display){
        try{
            this.display = display;
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                if(ClientHolder.getInstance().isSendMessage()) {
//                    System.out.println("xddddddddddd");
                    String message;// = scanner.nextLine();
                    message = ClientHolder.getInstance().getTextField().getText();
                    ClientHolder.getInstance().getTextField().clear();
                    bufferedWriter.write(username + ": " + message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    ClientHolder.getInstance().setSendMessage(false);
                }else {
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;
                String display_Message = "";

                while (socket.isConnected()){
                    try {
                        messageFromGroupChat = bufferedReader.readLine();
                        if(messageFromGroupChat.isBlank()){
                            display_Message = "";
                        }

                        if(!messageFromGroupChat.isBlank() && messageFromGroupChat != null){
                            display_Message += messageFromGroupChat + "\n";
                        }
                        System.out.println(messageFromGroupChat);
                        display.setText(display_Message);
                        System.out.println("==========\n"+display_Message+"=============");
                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if(socket != null) socket.close();
            if(bufferedReader != null) bufferedReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("127.0.0.1", 1234);
        Client client = new Client(socket, username, new TextArea());
//        System.out.println("Successfully connected to chat!");

        client.listenForMessage();
        client.sendMessage();
    }

    @Override
    public void run() {

    }
}













