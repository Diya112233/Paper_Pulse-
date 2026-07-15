package com.example.project_news.billcollect;

import java.io.FileInputStream;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import com.example.project_news.newspapertable.NewspaperBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BillCollector {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgadmin;

    @FXML
    private Label lblcolsumm;

    @FXML
    private TableView<BillingBean> tblcollection;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    private TextField txtmobile;



    @FXML
    void doGetPendingBills(ActionEvent event) {


            tblcollection.setItems(null);
            tblcollection.setItems(getAllPendingBills());




    }

    @FXML
    void doPaid(ActionEvent event) {
        try {
            String query = "update billing set status = ? where mobilenumber = ? and status = 1";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, 0); // mark as paid
            pst.setString(2, txtmobile.getText());

           pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    Connection con=null;
    @FXML
    void initialize() {

        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
        }

        TableColumn<BillingBean, String> dtfromC=new TableColumn<BillingBean, String>("From date: ");
        dtfromC.setCellValueFactory(new PropertyValueFactory<BillingBean,String>("dos"));
        dtfromC.setMinWidth(100);

        TableColumn<BillingBean, String> dttoC=new TableColumn<BillingBean, String>("To date: ");
        dttoC.setCellValueFactory(new PropertyValueFactory<BillingBean,String>("doe"));
        dttoC.setMinWidth(100);

        TableColumn<BillingBean, String> daysC=new TableColumn<BillingBean, String>("No.of days: ");
        daysC.setCellValueFactory(new PropertyValueFactory<BillingBean,String>("days"));
        daysC.setMinWidth(100);

        TableColumn<BillingBean, String> lessdaysC=new TableColumn<BillingBean, String>("Less days");
        lessdaysC.setCellValueFactory(new PropertyValueFactory<BillingBean,String>("lessdays"));
        lessdaysC.setMinWidth(100);

        TableColumn<BillingBean, String> billC=new TableColumn<BillingBean, String>("Total Bill");
        billC.setCellValueFactory(new PropertyValueFactory<BillingBean,String>("bill"));
        billC.setMinWidth(100);




        tblcollection.getColumns().addAll(dtfromC,dttoC,daysC,lessdaysC,billC);

        try{
         //   imgadmin.setImage(new Image(new FileInputStream("C:/Users/NEW/OneDrive/Pictures/Screenshots/Screenshot 2025-07-17 235711.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    ObservableList<BillingBean> getAllPendingBills()
    {
        ObservableList<BillingBean> arr= FXCollections.observableArrayList();
        try{
            String query="select * from billing where mobilenumber=? and status=?";
            PreparedStatement pst=con.prepareStatement(query);
            pst.setString(1,txtmobile.getText());
            pst.setInt(2,1);
            ResultSet res=pst.executeQuery();
            float amount=0;

            while(res.next())
            {
                String dos= String.valueOf(res.getDate("dos"));
                String doe= String.valueOf(res.getDate("doe"));
                String lessdays= String.valueOf(res.getInt("days"));
                String bill=String.valueOf(res.getFloat("bill"));
                long diffMillis = res.getDate("doe").getTime() - res.getDate("dos").getTime();
                long totalDays = (diffMillis / (1000 * 60 * 60 * 24)) + 1;
                amount+=res.getFloat("bill");
                String days=String.valueOf(totalDays);
                BillingBean blt=new BillingBean(dos,doe,days,lessdays,bill);
                arr.add(blt);

            }
            txtTotalAmount.setText(String.valueOf(amount));


        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
        return arr;
    }

}
