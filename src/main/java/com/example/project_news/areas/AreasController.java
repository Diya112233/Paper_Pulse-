package com.example.project_news.areas;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AreasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtArea;

    Connection con=null;
    @FXML
    void doSave(ActionEvent event) {
        try{
        String query = "insert into areas values (?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, txtArea.getText());
        pst.executeUpdate();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();

    }

}
