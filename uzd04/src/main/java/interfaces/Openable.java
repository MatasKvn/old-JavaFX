package interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface Openable {

    @FXML
    void onOpenExcelFile(ActionEvent event);
}
