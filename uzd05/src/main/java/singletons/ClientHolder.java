package singletons;

import javafx.scene.control.TextField;
import server.Client;

public class ClientHolder {
    private Client client;

    public TextField getTextField() {
        return textField;
    }

    private TextField textField;

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }

    private boolean sendMessage = false;

    public boolean isSendMessage() {
        return sendMessage;
    }

    private final static ClientHolder INSTANCE = new ClientHolder();
    public void setClient(Client client){
        this.client = client;
    }

    public Client getClient(){
        return client;
    }

    public static ClientHolder getInstance(){
        return INSTANCE;
    }
}
