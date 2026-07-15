package com.example.project_news.billing;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BillingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnSaveBill;

    @FXML
    private Button btnfetch;

    @FXML
    private DatePicker dtDos;

    @FXML
    private DatePicker dtUpto;

    @FXML
    private TextField txtMob;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTotalBill;

    @FXML
    private TextField txtTotalprice;

    @FXML
    private TextField txtless;

    @FXML
    void doGenerate(ActionEvent event) {
        float total=0;
        int tdays=dtUpto.getValue().getDayOfYear()-dtDos.getValue().getDayOfYear()-Integer.parseInt(txtless.getText());
        float totalperday=Float.parseFloat(txtTotalprice.getText());
        txtTotalBill.setText(String.valueOf(tdays*totalperday));
    }

    void doUpdate()
    {
        String query="update customers set dos=? where mobile=?";
        LocalDate doe = dtUpto.getValue();
        Date dt= Date.valueOf(doe.plusDays(1));

        try
        {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setDate(1,dt);
            pst.setString(2,txtMob.getText());
            pst.executeUpdate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @FXML
    void doSaveBill(ActionEvent event) {
        String query = "insert into billing (mobilenumber, dos, doe, days, bill, status) values (?, ?, ?, ?, ?, ?)";
        try {
            //create table billing (rid int primary key auto_increment, mobilenumber varchar(10),dos date,doe date,days int,bill float,status int default 0);
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,txtMob.getText());
            pst.setDate(2,java.sql.Date.valueOf(dtDos.getValue()));
            pst.setDate(3,java.sql.Date.valueOf(dtUpto.getValue()));
            pst.setString(4,String.valueOf(txtless.getText()));
            pst.setString(5,String.valueOf(txtTotalBill.getText()));
            pst.setInt(6,1);
            pst.executeUpdate();
            System.out.println("Record Saved");
            doUpdate();


        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }


    }

    @FXML
    void dofetch(ActionEvent event) {
        String mob=txtMob.getText();
        String query="select * from customers where mobile=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMob.getText());
            ResultSet arr= pst.executeQuery();
            while(arr.next())
            {
                txtTotalprice.setText(arr.getString("emailid"));
                txtName.setText(arr.getString("cname"));
                txtTotalprice.setText(arr.getString("totalbill"));
                dtDos.setValue(arr.getDate("dos").toLocalDate());


            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    Connection con=null;
    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

        dtUpto.setValue(LocalDate.now());
        txtless.setText(String.valueOf(0));
        txtTotalBill.setText("Not Calculated yet!!");


    }

}
