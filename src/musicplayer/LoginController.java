package musicplayer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileReader;
import java.io.IOException;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane panel_login;
    @FXML
    private Button btn_login;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private AnchorPane panel_signup;
    @FXML
    private TextField txt_username_up;
    @FXML
    private TextField txt_password_up;
    @FXML
    private TextField txt_email_up;
    @FXML
    private Button btn_signup;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public String path = "/Volumes/KOJI TANAKA/TAFE semester 2/java3/MusicPlayer/csvUsers.csv";
    //public String path = "D:/TAFE semester 2/java3/MusicPlayer/csvUsers.csv ";
    //public String path = "C:/Users/30037795/Desktop/csvUsers.csv ";/Volumes/KOJI\ TANAKA/TAFE\ semester\ 2/java3/MusicPlayer/csvUsers.csv
    //Fiel file = F

    List<String[]> usersList = new ArrayList<>();
    //List<CSVUser> usersList = ArrayList();
    static Socket socket;
    String line="";
    String splitBy = ",";
    String[] user;
    

    public void ReadFiles() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) //returns a Boolean value  
            {
                int i = 0;
                String[] user = line.split(splitBy); // use comma as separator 
                System.out.println(user[0]);
                // personList[i] = new Person(employee[0], employee[1], employee[2], employee[3], employee[4], employee[5]);
                usersList.add(user);
                //list.add(personList[i]);
                i++;
                //tableView.setItems(FXCollections.observableArrayList(person));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void make_new_user(ActionEvent event) throws Exception, CsvValidationException {
        String username = txt_username_up.getText();
        String password = txt_password_up.getText();
        String email = txt_email_up.getText();
        //get salt
        byte[] salt = getSalt();
        //make secure passwrod using hash technique
        String securePassword = getSecurePassword(password);

        System.out.println("This is secure pass" + securePassword);
        //System.out.println(aUser.getName()+ aUser.getPassword()+ aUser.getEmail());
        String[] csvRecord = {username, securePassword, email};
        usersList.add(csvRecord);
        //open csv
        try (var fos = new FileOutputStream(path);
                var osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                var writer = new CSVWriter(osw)) {
            writer.writeAll(usersList);
        }
    }

    @FXML
    public void LoginpanelShow() {
        System.out.println("Login pane is working.");
        panel_login.setVisible(true);
        panel_signup.setVisible(false);
    }

    @FXML
    public void SignuppanelShow() {
        System.out.println("Signup pane is working.");
        panel_login.setVisible(false);
        panel_signup.setVisible(true);
    }

    @FXML
    public void Login(ActionEvent event) throws Exception {
        System.out.println("Login button is working.");
        String userinput = txt_username.getText();
        String passinput = txt_password.getText();
        
        byte[] salt = getSalt();
        //make secure passwrod using hash technique
        String securePassword = getSecurePassword(passinput);

        for (String[] user : usersList) {
            System.out.println(user[0] +user[1] );
            if (userinput.equals(user[0])&& securePassword.equals(user[1])) {
                btn_login.getScene().getWindow().hide();
                Parent root
                        = FXMLLoader.load(getClass().getResource("SelectionPage.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        try {
            ReadFiles();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getSecurePassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((byte) 1111);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private List<CSVUser> ArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

//        conn = mysqlconnect.ConnectDb();
//         String sql = "Select*from users where username = ? and password = ?";
//         
//         try{
//             System.out.println("try is working.");
//             pst = conn.prepareStatement(sql);
//             pst.setString(1, txt_username.getText());
//             pst.setString(2, txt_password.getText());
//             rs = pst.executeQuery();
//             if(rs.next()){
//                 System.out.println("if is working.");
//                 JOptionPane.showMessageDialog(null, "Username and Password is correct");
//             }
//             else{
//                 System.out.println("else is working.");
//                 JOptionPane.showMessageDialog(null, "Invalid Username or Password");
//             }
//         }
//         catch(Exception e){
//             System.out.println("catch is working.");
//             System.out.println("Log in error");
//             JOptionPane.showMessageDialog(null, e);
//             
//         }
//    @FXML
//    private void Login (ActionEvent event)throws Exception{
//         conn = mysqlconnect.ConnectDb();
//         String sql = "Select*from users where username = ? and password = ?";
//         try{
//             pst = conn.prepareStatement(sql);
//             pst.setString(1, txt_username.getText());
//             pst.setString(2, txt_password.getText());
//             rs = pst.executeQuery();
//             if(rs.next()){
//                 JOptionPane.showMessageDialog(null, "Username and Password is correct");
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Invalid Username or Password");
//             }
//         }
//         catch(Exception e){
//             System.out.println("Log in error");
//             JOptionPane.showMessageDialog(null, e);
//             
//         }
