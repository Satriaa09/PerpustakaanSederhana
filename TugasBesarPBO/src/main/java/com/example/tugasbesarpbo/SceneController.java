package com.example.tugasbesarpbo;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SceneController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private FXMLLoader root;
    @FXML
    private TextField nimNumber;
    @FXML
    private PasswordField password;
    @FXML
    private Text errorLoginMassage;

    int index = -1;

    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    // table controller
    public void loginBtnClicked(ActionEvent event) throws IOException {
        conn = sqlConnector.ConnectDb();
        //String sql = "select * from signup where NimNumber = ? and password = ? ";
        try {
            String sql = "select * from signup where NimNumber = ? and password = ? ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, nimNumber.getText());
            pst.setString(2, password.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Login Pressed");
                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                errorLoginMassage.setText("Login Failed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public void BtnPeminjamanclicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("daftar-buku.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void BtnPengambalianclicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("pengembalian-buku.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logoutbtn(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void RegistClicked(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}