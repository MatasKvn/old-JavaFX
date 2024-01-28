package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("");
        primaryStage.show();


    }


    public static void main(String[] args){
        launch();
    }


}
/*
Duomenys:
id, first_name, last_name, email, imagelink, ip_address
Saugoti i faila pavadinimu vardo, pavardes pirmos 3 raides, 3 paskutiniai ip skaiciai(po paskutinio tasko)(pvz: matkva999)

Veiksmai:
- Nuskaityt faila, sudet (jį?) lentele
- Vienos eilutės(zmogaus) duomenis saugoti atskirame faile
- Sukurus faila istrinti irasa is lenteles
- Sukurti dar viena lentele(atiluktu darbų sarašas)(realiu laiku atnaujinami duomenys)
- Ištrinti faila ant jo paspaudus atliktu darbu lenteleje
- Darbui išsitrinus, (tai?) matosi kitoje lentelėje, kurioje nuskaitomi įrašai
- Mygtukas ištrinti visus failus

 */