package musicplayer;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JFileChooser;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayerController extends ListCell<String> implements Initializable {

    static AVLTree avl = new AVLTree();
    @FXML
    private Label labelFind;
    @FXML
    private ListView<String> listBox;
    private ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private TextField tbChosenFile;
    @FXML
    private TextField searchTextBox;

    @FXML
    private TextField currentSong;

    static File songPlayed;
    static simpleAudioPlayer audioPlayer;
    
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button restartButton;
    @FXML
    private Button btn_search;
    @FXML
    private Button btn_load;
    @FXML
    private Label searchLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Click the choose file button
    @FXML
    private void openDialog(ActionEvent event) {

        final JFrame iFRAME = new JFrame();
        iFRAME.setAlwaysOnTop(true);    // ****
        iFRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iFRAME.setLocationRelativeTo(null);
        iFRAME.requestFocus();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //choose directry
                //jfc.showSaveDialog(null);
                int returnValue = jfc.showOpenDialog(iFRAME);   // ***
                iFRAME.dispose();

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    // Display selected file in console
                    System.out.println(selectedFile.getAbsolutePath());
                    tbChosenFile.setText(selectedFile.getAbsolutePath());
                } else {
                    System.out.println("No File Selected!");
                }
            }
        });
    }

    @FXML
    private void loadMusics(ActionEvent event) {
        File dir = new File(tbChosenFile.getText()); 
        //File dir = new File("/Volumes/KOJI TANAKA/TAFE semester 2/java3/convertedMusic"); 
        System.out.println("Path is " + tbChosenFile.getText());

        for (File file : dir.listFiles()) {
            avl.Add(file);
        }
        String[] words = avl.Display().split(",");
        //list.add(avl.Display());
        listBox.getItems().addAll(words);
       
        songPlayed = avl.FindAndReturn(listBox.getItems().get(0));
        currentSong.setText(songPlayed.getName());
    }

    @FXML
    private void playMusic(ActionEvent event) {
        try {
            if (audioPlayer == null || audioPlayer.status.equals("paused")) {          
                if(audioPlayer == null){
                    audioPlayer = new simpleAudioPlayer(songPlayed);
                    audioPlayer.play();
                } 
                else {
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
    private void pauseMusic(ActionEvent event) {
        if (audioPlayer == null) {
        } else {
            audioPlayer.pause();
        }
    }
    @FXML
    private void reStartMusic(ActionEvent event) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (audioPlayer == null) {
        } else {
            System.out.println("Restart is working 1");
            audioPlayer.restart();
            
            System.out.println("Restart is working 2");
            audioPlayer = new simpleAudioPlayer(songPlayed);
                audioPlayer.play();
        }
    }

    @FXML
    private void searchMusic(ActionEvent event) {
        avl.Find(searchTextBox.getText());
        System.out.println(avl.Find(searchTextBox.getText()));
        if (avl.Find(searchTextBox.getText()).equals("found!")) {
            listBox.getItems().clear();
            listBox.getItems().addAll(searchTextBox.getText());

            songPlayed = avl.FindAndReturn(searchTextBox.getText());

            labelFind.setText("Found!");
            System.out.println("success");
            System.out.println(avl.Find(searchTextBox.getText()));
            //listBox.getItems().indexOf(avl.Find(searchTextBox.getText()));
            //System.out.println(listBox.getItems().indexOf(avl.Find(searchTextBox.getText())));
            listBox.focusModelProperty();

            int searchInd = listBox.getItems().indexOf(searchTextBox.getText());
            System.out.println("index of searched music is " + searchInd);
            listBox.getFocusModel().focus(searchInd);

            System.out.println(listBox.getFocusModel().getFocusedItem());
            currentSong.setText(listBox.getFocusModel().getFocusedItem());
        } else {
            labelFind.setText("There is no such song.");
            String[] words = avl.Display().split(",");

            //list.add(avl.Display());
            listBox.getItems().clear();
            listBox.getItems().addAll(words);
            System.out.println("Something wrong");
            System.out.println(avl.Find(searchTextBox.getText()));
        }
    }

    @FXML
    private void musicSelect(MouseEvent event) {
        songPlayed = avl.FindAndReturn(listBox.getItems().get(listBox.getFocusModel().getFocusedIndex()));
        currentSong.setText(songPlayed.getName());  
    }

    

}
