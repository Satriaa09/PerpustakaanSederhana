package com.example.tugasbesarpbo;


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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class PengembalianController implements Initializable{
    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

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
        private TableColumn<Customer, String> tanggalCol;

        @FXML
        private Text terlambatInfo;

        @FXML
        private Text denda;


        //Text input
        @FXML
        private TextField namaBukuInput;

        @FXML
        private TextField peminjamInput;

        @FXML
        private TextField tanggalPengembalianInput;


        ObservableList<Customer> listM;
        ObservableList<Customer> dataList;

        @FXML
        void backBtnClicked(ActionEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    @FXML
    void getSelected (MouseEvent event){
        index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        namaBukuInput.setText(bukuCol.getCellData(index));
        peminjamInput.setText(peminjamCol.getCellData(index));
        tanggalPengembalianInput.setText(tanggalCol.getCellData(index));

        String tanggalKembali = tanggalPengembalianInput.getText();
        String[] split = tanggalKembali.split("/");
        String tanggal = split[0];
        String bulan  = split[1];
        String tahun = split[2];
        int date = parseInt(tanggal);
        int month = parseInt(bulan);
        int year = parseInt(tahun);

        System.out.println(tanggalKembali);

        LocalDate ld = LocalDate.now();
        DateTimeFormatter tanggalFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateSekarang = ld.format(tanggalFormat);
        String[] split_2 = dateSekarang.split("/");
        String tgl = split_2[0];
        String bln  = split_2[1];
        String thn = split_2[2];
        int dateNow =  parseInt(tgl);
        int monthNow =  parseInt(bln);
        int yearNow =  parseInt(thn);

        System.out.println(dateSekarang);

        System.out.println(year + " " + yearNow);
        System.out.println(month + " " + monthNow);
        System.out.println(date + " " + dateNow);

        if(yearNow >= year){
            if (monthNow >= month || yearNow >= year){
                if (dateNow > date && (monthNow >= month && yearNow >= year)){
                    System.out.println("Pengembalian Terlambat !!");
                    terlambatInfo.setText("* Pengembalian buku terlambat !!");
                    int Denda = dateNow - date;
                    denda.setText("Denda yang dibebankan : " + Denda*5000);

                } else {
                    terlambatInfo.setText("");
                    denda.setText("");
                }
            }
        }
    }

    public void submit(){
        conn = sqlConnector.ConnectDb();

        String sql = "delete from raksatu where nama_peminjam = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, peminjamInput.getText()); // YANG INIII
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }



    }

    @FXML
    void search_user() {
        bukuCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaBuku"));
        peminjamCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaPeminjam"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("tanggalPengembalian"));



        dataList = sqlConnector.getDatausers();
        tableView.setItems(dataList);
        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);
        peminjamInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNamaPeminjam().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches username
                }
                else
                    return false; // Does not match.
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public void UpdateTable(){
        bukuCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaBuku"));
        peminjamCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("namaPeminjam"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("tanggalPengembalian"));

        listM = sqlConnector.getDatausers();
        tableView.setItems(listM);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        search_user();
    }
}

