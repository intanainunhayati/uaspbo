/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import javax.swing.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Lenovo
 */
public class javaconnect {
    
    Connection conn = null;
    public static Connection ConnecrDb() {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Lenovo\\Documents\\2KS2\\SEMESTER 4\\PBO\\uas\\uaspbo.db ");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
            
        }
    }
    
     public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
