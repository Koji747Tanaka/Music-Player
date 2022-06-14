package musicplayer;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.util.concurrent.ThreadLocalRandom;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RLGameController implements Initializable {

    @FXML
    private Button btn_play;
    @FXML
    private Button btn_L;
    @FXML
    private Button btn_R;

    List<Word> audioList = new <Word>ArrayList();

    String Rpath = "/Users/koji/convertedMusic";  ///Users/koji/convertedMusic
    String Lpath = "/Users/koji/convertedMusic"; ///Volumes/KOJI TANAKA/TAFE semester 2/java3/convertedMusic
    static File songPlayed;
    static simpleAudioPlayer audioPlayer;
    static int LRsound;
    @FXML
    private Label answerCheck;
    @FXML
    private Button btn_menu;
    @FXML
    private Button btn_next;
    
    
    static int correctNum =0;
    static int wrongNum =0;
    
    @FXML
    private Text correctNumTxt;
    @FXML
    private Text wrongNumTxt;
    

    @FXML
    private void playWord(ActionEvent event) {
        playWord();
    }
    private void playWord(){
        try {
            if (audioPlayer == null || audioPlayer.status.equals("paused")) {
                if (audioPlayer == null) {
                    audioPlayer = new simpleAudioPlayer(songPlayed);
                    audioPlayer.play();
                } else {
                    audioPlayer.play();
                }
            } else {
                audioPlayer.stop();
                audioPlayer = new simpleAudioPlayer(songPlayed);
                audioPlayer.play();
            }

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    @FXML
    private void answerL(ActionEvent event) {
        if (LRsound == 0) {
            answerCheck.setText("Correct");
            correctNum++;
            correctNumTxt.setText(String.format("%d",correctNum));
        } else {
            answerCheck.setText("Wrong");
            wrongNum++;
            wrongNumTxt.setText(String.format("%d",wrongNum));
        }
        disableLRButtons();
        
        btn_L.setStyle("-fx-border-color: blue;\n"); //"-fx-border-color: blue;\n" "-fx-background-color: #ff0000; "
        
    }

    @FXML
    private void answerR(ActionEvent event) {
        if (LRsound == 1) {
            answerCheck.setText("Correct");
            correctNum++;
            correctNumTxt.setText(String.format("%d",correctNum));
        } else {
            answerCheck.setText("Wrong");
            wrongNum++;
            wrongNumTxt.setText(String.format("%d",wrongNum));
        }
        disableLRButtons();
        btn_R.setStyle("-fx-border-color: blue;\n");
    }

    public void selectPlayedSong() {
        int listLength = audioList.size();
        int randomNum = ThreadLocalRandom.current().nextInt(0, listLength);
        LRsound = ThreadLocalRandom.current().nextInt(0, 2); // 0 or 1 0 is for L, 1 is for R
        
        String[] displayL = audioList.get(randomNum).l.getName().split(".w");
        String[] displayR = audioList.get(randomNum).r.getName().split(".w");
        
        btn_L.setText(displayL[0]);
        btn_R.setText(displayR[0]);
        
        if (LRsound == 0) // 0 is for L, 1 is for R
        {
            songPlayed = audioList.get(randomNum).l;
            //songPlayed = audioList.get(1).l;
        } else {
            songPlayed = audioList.get(randomNum).r;
            //songPlayed = audioList.get(1).r;
        }
    }

    public void fileDownLoad() {
        File rFile = new File(Rpath);
        int i = 0;
        for (File file : rFile.listFiles()) {
            audioList.add(i, new Word());
            audioList.get(i).r = file;
            i++;
        }

        i = 0;
        File lFile = new File(Rpath);
        for (File file : lFile.listFiles()) {
            //audioList.add(j, new Word());
            audioList.get(i).l = file;
            i++;
        }

        selectPlayedSong();
        System.out.println("List length is" + audioList.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileDownLoad();
    }

    @FXML
    private void goBackToMenu(ActionEvent event) throws Exception {
        audioPlayer.stop();
        btn_menu.getScene().getWindow().hide();
        Parent root
                = FXMLLoader.load(getClass().getResource("SelectionPage.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    private void playNextWord(ActionEvent event) {
        btn_L.setStyle("-fx-border-color: none;\n");
        btn_R.setStyle("-fx-border-color: none;\n");
        selectPlayedSong();
        playWord();
        activateLRButtons();
    }
    private void disableLRButtons(){
        btn_L.setDisable(true);
        btn_R.setDisable(true);
    }
    private void activateLRButtons(){
        btn_L.setDisable(false);
        btn_R.setDisable(false);
    }

}
