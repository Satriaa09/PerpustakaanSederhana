package com.example.tugasbesarpbo;


import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

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
    private TextField NIMRegis;

    @FXML
    private TextField PasswordRegis;

    @FXML
    private TextField PasswordConfirm;

    @FXML
    private Text confirmError;


    ObservableList<Customer> listM;
    ObservableList<Customer> dataList;



    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    public void BtnSignUPClicked(ActionEvent event){

        if (PasswordRegis.getText().equals(PasswordConfirm.getText())) {
            System.out.println("Sign up clicked");
            confirmError.setText("");
            conn = sqlConnector.ConnectDb();
            String sql = "insert into signup (NimNumber,password)values(?,?)";
                    try{
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, NIMRegis.getText());
                        pst.setString(2, PasswordRegis.getText());
                        pst.execute();

                        JOptionPane.showMessageDialog(null, "Akun berhasil Dibuat");
                        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
        }else{
            confirmError.setText("Password Tidak Sesuai");
        }
    }

    public void UpdateTable(){
        listM = sqlConnector.getDatausers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable();
    }

    @FXML
    public void backbtnclicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}