package interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface Savable {

    @FXML
    void onSaveToPDF(ActionEvent event);

    @FXML
    void onSaveToExcel(ActionEvent event);
}
