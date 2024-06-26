/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Mahasiswa;
import java.sql.*;

public class Database implements Serializable {
    private Connection connect() { 
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\Lenovo\\Documents\\2KS2\\SEMESTER 4\\PBO\\uas\\uaspbo.db"; 
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void tambahMahasiswa(Mahasiswa mhs) throws SQLException {
        String sql = "INSERT INTO mahasiswa(nim, nama, email, JenisKelamin, Dosen, Sesi) VALUES(?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mhs.getNim());
            pstmt.setString(2, mhs.getNama());
            pstmt.setString(3, mhs.getEmail());
            pstmt.setString(4, mhs.getJenisKelamin());
            pstmt.setString(5, mhs.getDosen());
            pstmt.setString(6, mhs.getSesi());
            pstmt.executeUpdate();
        } finally {
            closeConnection(conn);
        } 
    }

    public ArrayList<Mahasiswa> getMahasiswaList() throws SQLException {
        ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa";
Connection conn = null;
        try {
            conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa();
                mhs.setNim(rs.getString("nim"));
                mhs.setNama(rs.getString("nama"));
                mhs.setEmail(rs.getString("email"));
                mhs.setJenisKelamin(rs.getString("JenisKelamin"));
                mhs.setDosen(rs.getString("Dosen"));
                mhs.setSesi(rs.getString("Sesi"));
                mahasiswaList.add(mhs);
            }
        } finally {
            closeConnection(conn);
        }
        return mahasiswaList;
    }
    
    public boolean validateUser(String nim, String password) throws SQLException {
        boolean isValid = false;
        String query = "SELECT * FROM user WHERE nim =? AND password =?";
Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nim);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } finally {
            closeConnection(conn);
        }
        return isValid;
    }

    public Mahasiswa getMahasiswa(String nim) throws SQLException {
        Connection conn = getConnection();
        Mahasiswa mhs = null;
        try {
            String sql = "SELECT * FROM mahasiswa WHERE nim=? LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nim);

            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                mhs = new Mahasiswa();
                mhs.setNim(res.getString("nim"));
                mhs.setNama(res.getString("nama"));
                mhs.setEmail(res.getString("email"));
                mhs.setJenisKelamin(res.getString("JenisKelamin"));
                mhs.setDosen(res.getString("Dosen"));
                mhs.setSesi(res.getString("Sesi"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(conn);
        }
        return mhs;
    }
    
    public void hapusMahasiswa(String nim) throws SQLException {
        String sql = "DELETE FROM mahasiswa WHERE nim=?";
Connection conn = null;
        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nim);
            pstmt.executeUpdate();
        } finally {
            closeConnection(conn);
        }
    }
    
    public void editMahasiswa(String nim, String nama, String email, String jenisKelamin, String dosen, String sesi) throws SQLException {
        String sql = "UPDATE mahasiswa SET nama=?, email=?, JenisKelamin=?, Dosen=?, Sesi=? WHERE nim=?";
Connection conn = null;
        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nama);
            pstmt.setString(2, email);
            pstmt.setString(3, jenisKelamin);
            pstmt.setString(4, dosen);
            pstmt.setString(5, sesi);
            pstmt.setString(6, nim);
            pstmt.executeUpdate();
        } finally {
            closeConnection(conn);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Lenovo\\Documents\\2KS2\\SEMESTER 4\\PBO\\uas\\uaspbo.db");
    } 
}