package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.SubScene;
import javafx.scene.control.TableView;

public abstract class CustomTableComponent extends TableView {


    private ObservableList<?> data;

    public ObservableList<?> getData() {
        return data;
    }

    public CustomTableComponent(ObservableList<?> data){
        this.data = data;
        setItems(FXCollections.observableArrayList(data));
    }

    public abstract void createColumns();
    public void setLayout(double x, double y, double width, double height){
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(width, height);
    }

    public void setData(ObservableList<?> data){
        this.data = data;
        setItems(data);
    }

}
