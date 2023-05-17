package com.example.tugasbesarpbo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.JOptionPane;


public class PeminjamanController implements Initializable  { //implements Initializable


    @FXML
    private Stage stage;

    //Table
    @FXML
    private TableView<Customer> tableView;

    //Columns
    @FXML
    private TableColumn<Customer, String> bukuCol;

    @FXML
    private TableColumn<Customer, String> peminjamCol;

    @FXML
    private TableColumn<Customer, Integer> tanggalCol;


    //Text input
    @FXML
    private TextField namaBukuInput;

    @FXML
    private TextField peminjamInput;

    @FXML
    private TextField tanggalPengembalianInput;


    ObservableList<Customer> listM;
    ObservableList<Customer> dataList;



    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void submit(){
        conn = sqlConnector.ConnectDb();
        //String sql = "insert into users (username,password,email,type)values(?,?,?,? )";
        String sql = "insert into raksatu (nama_buku,nama_peminjam,tanggal_pengembalian)values(?,?,? )";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, namaBukuInput.getText());
            pst.setString(2, peminjamInput.getText());
            pst.setString(3, tanggalPengembalianInput.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable();
          //  search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void UpdateTable(){
        bukuCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaBuku"));
        peminjamCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaPeminjam"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("tanggalPengembalian"));

        listM = sqlConnector.getDatausers();
        tableView.setItems(listM);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
    }
}