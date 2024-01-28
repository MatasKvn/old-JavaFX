package server;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    private BufferedWriter logWriter;


    private TextArea display;
    public ClientHandler(Socket socket, BufferedWriter logWriter, TextArea display){
        try {
            this.display = display;
            this.logWriter = logWriter;
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
//            System.out.println(clientUsername + " has connected!");

            //log
//            logWriter = new BufferedWriter(new FileWriter(new File("src/main/resources/logs/log.txt"), true));
//            logWriter.write(clientUsername + " has connected!" + "\n");
//            logWriter.close();

//            broadcastMessage("Server: " + clientUsername + " has connected!");
            addMsgToLog("Server: " + clientUsername + " has connected!");
        }catch (IOException e){
             closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try {
                messageFromClient = bufferedReader.readLine();
//                System.out.println(messageFromClient);
                addMsgToLog(messageFromClient);

            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void addMsgToLog(String messageFromClient){
        try{
            //log (append to log)
            logWriter = new BufferedWriter(new FileWriter(new File("src/main/resources/logs/log.txt"), true));
            logWriter.write(messageFromClient + "\n");
            logWriter.close();

            // read log and send to clients
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/logs/log.txt"));
//                messageFromClient = "";

            String display_text = "";
            String line = "";

            broadcastMessage("");
            do {
                if(line == null) break;
                line = reader.readLine();
//                    line += '\n';

                if(line != null){
                    broadcastMessage(line);
                    display_text += line + "\n";
                }
//                    messageFromClient += line;
            }while (line != null);

            display.setText(display_text);

            reader.close();
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void broadcastMessage(String message){
        System.out.println(message);
        for (int i=0; i<ClientHandler.clientHandlers.size(); ++i){
            try {
                if(true){ //!ClientHandler.clientHandlers.get(i).equals(this)
                    clientHandlers.get(i).bufferedWriter.write(message);
                    clientHandlers.get(i).bufferedWriter.newLine();
                    clientHandlers.get(i).bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
//        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
        addMsgToLog("SERVER: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();

        try {
            if(logWriter != null) logWriter.close();
            if(socket != null) socket.close();
            if(bufferedReader != null) bufferedReader.close();
            if(bufferedWriter != null) bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



















