package com.example.tugasbesarpbo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class sqlConnector {
    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/db_rakbuku","root","");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static ObservableList<Customer> getDatausers(){
        Connection conn = ConnectDb();
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from raksatu");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Customer(rs.getString("nama_buku"), rs.getString("nama_peminjam"), rs.getString("tanggal_pengembalian")));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
