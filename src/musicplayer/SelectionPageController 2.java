package musicplayer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelectionPageController implements Initializable {
    @FXML
    private Button btn_game;
    @FXML
    private Button btn_tongue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openGame(ActionEvent event) throws Exception {
        btn_game.getScene().getWindow().hide();
                Parent root
                        = FXMLLoader.load(getClass().getResource("LRGame.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

    @FXML
    private void openTwister(ActionEvent event) throws Exception{
        btn_tongue.getScene().getWindow().hide();
                Parent root
                        = FXMLLoader.load(getClass().getResource("musicPlayer.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }



    
    
}
