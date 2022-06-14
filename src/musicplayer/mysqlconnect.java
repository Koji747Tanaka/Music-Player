/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;
import java.sql.*;  
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class mysqlconnect {
    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            System.out.print("Connection class try start is working");
            Class.forName("com.mysql.jdbc.Driver");  
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:8080/MusicPlayer", "root", "");  
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8080/MusicPlayer","root","");
            System.out.print("Connection class try con is working");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }  
    }
}
