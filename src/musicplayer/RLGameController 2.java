package musicplayer;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import static musicplayer.MusicPlayerController.audioPlayer;
import static musicplayer.MusicPlayerController.songPlayed;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.control.Label;

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
    private void playWord(ActionEvent event) {
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
        }
        else{
            answerCheck.setText("Wrong");
        }
    }

    @FXML
    private void answerR(ActionEvent event) {
        if (LRsound == 1) {
            answerCheck.setText("Correct");
        }
        else{
            answerCheck.setText("Wrong");
        }
    }

    public void selectPlayedSong() {
        int listLength = audioList.size();
        int randomNum = ThreadLocalRandom.current().nextInt(0, listLength);
        LRsound = ThreadLocalRandom.current().nextInt(0, 2); // 0 or 1
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

}
