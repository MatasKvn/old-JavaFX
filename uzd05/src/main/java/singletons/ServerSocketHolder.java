package singletons;

import java.net.ServerSocket;

public class ServerSocketHolder {
    private ServerSocket serverSocket;

    private final static ServerSocketHolder INSTANCE = new ServerSocketHolder();

    public static ServerSocketHolder getInstance(){
        return INSTANCE;
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
}
