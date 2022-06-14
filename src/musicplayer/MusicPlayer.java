package musicplayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicPlayer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Log in");
        Parent root
                = FXMLLoader.load(getClass().getResource("RLGame.fxml"));//musicPlayer.fxml  login.fxml
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
