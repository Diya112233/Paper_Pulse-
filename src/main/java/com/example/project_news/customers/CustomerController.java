package com.example.project_news.customers;


import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnStartPaper;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private ComboBox<String> comboHawker;

    @FXML
    private DatePicker dtdos;

    @FXML
    private ImageView imgprev;

    @FXML
    private ListView<String> lstPapers;

    @FXML
    private ListView<String> lstPrices;

    @FXML
    private ListView<String> lstSelPaper;

    @FXML
    private ListView<String> lstSelPrice;

    @FXML
    private TextField txtAdd;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMob;

    @FXML
    private TextField txtName;

    @FXML
    void doFillhawkers(ActionEvent event) {
        ArrayList<String> avlhawkers = new ArrayList<String>();

        try {
            PreparedStatement pst = con.prepareStatement("select hawkerid from hawkers where selareas like ?");
            String str = "%" + comboArea.getSelectionModel().getSelectedItem() + "%";
            pst.setString(1, str);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                avlhawkers.add(rs.getString("hawkerid"));
            }
            comboHawker.getItems().addAll(avlhawkers);
        }
        catch (Exception r)
        {
            r.printStackTrace();
        }


    }

    @FXML
    void doNew(ActionEvent event) {
        txtMob.clear();
        txtName.clear();
        txtEmail.clear();
        txtAdd.clear();
        dtdos.setValue(null);
        comboArea.getSelectionModel().clearSelection();
        comboHawker.getSelectionModel().clearSelection();

        lstSelPaper.getItems().clear();
        lstSelPrice.getItems().clear();

    }

    @FXML
    void doRemove(ActionEvent event) {
        try{
        String query="delete from customers where mobile =?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, txtMob.getText());}
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    @FXML
    void doDeletee(ActionEvent event) {

        String query="delete from customers where mobile=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMob.getText());
            pst.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Deleted");

        doNew(null);
    }

    @FXML
    void doStart(ActionEvent event) {
            try {
                String papers = String.join(",", lstSelPaper.getItems());
                String prices = String.join(",", lstSelPrice.getItems());

                String query = "insert into customers values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, txtMob.getText());
                pst.setString(2, txtName.getText());
                pst.setString(3, txtEmail.getText());
                pst.setString(4, txtAdd.getText());
                pst.setDate(5, java.sql.Date.valueOf(dtdos.getValue()));
                pst.setString(6, comboArea.getSelectionModel().getSelectedItem());
                pst.setString(7, comboHawker.getSelectionModel().getSelectedItem());
                pst.setString(8, papers);
                pst.setString(9, prices);
                pst.setInt(10, 1);
                int total=0;
                ObservableList<String> allPrices= lstSelPrice.getItems();
                for(String itr: allPrices)
                {
                   total+= Float.parseFloat(itr);
                }
                pst.setFloat(11,total);

                int count = pst.executeUpdate();

                if (count > 0) {
                    System.out.println("Customer saved successfully!!!!");
                } else {
                    System.out.println("error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        doNew(null);
    }

    @FXML
    void doremove(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            int ind=lstSelPaper.getSelectionModel().getSelectedIndex();
            lstSelPaper.getItems().remove(ind);
            lstSelPrice.getItems().remove(ind);
        }

    }

    @FXML
    void doselect(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            String selItem=lstPapers.getSelectionModel().getSelectedItem();
            lstSelPaper.getItems().add(selItem);

            int indx=lstPapers.getSelectionModel().getSelectedIndex();
            lstPrices.getSelectionModel().select(indx);

            lstSelPrice.getItems().add(lstPrices.getSelectionModel().getSelectedItem());

        }

    }
    Connection con=null;
//create table customers(mobile varchar(10) primary key,cname varchar(30), emailid varchar(30), address varchar(100),dos date,area varchar(30), hawkerid varchar(12),papers varchar(100),prices varchar(50),status int);
    @FXML
    void doFind(ActionEvent event) {

        String mob=txtMob.getText();
        String query="select * from customers where mobile=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,txtMob.getText());
            ResultSet arr= pst.executeQuery();
            while(arr.next())
            {
                txtEmail.setText(arr.getString("emailid"));
                txtName.setText(arr.getString("cname"));
                txtAdd.setText(arr.getString("address"));
                dtdos.setValue(arr.getDate("dos").toLocalDate());
                comboArea.getSelectionModel().select(arr.getString("area"));
                comboHawker.getSelectionModel().select(arr.getString("hawkerid"));
                lstSelPaper.getItems().clear();
                lstSelPrice.getItems().clear();

                String[] selpapers=arr.getString("papers").split(",");
                String[] selprices=arr.getString("prices").split(",");

                lstSelPaper.getItems().addAll(selpapers);
                lstSelPrice.getItems().addAll(selprices);

            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    @FXML
    void doUpdate(ActionEvent event) {
        try {
            // Join selected papers and prices as comma-separated strings
            String papers = String.join(",", lstSelPaper.getItems());
            String prices = String.join(",", lstSelPrice.getItems());

            float total = 0;
            for (String priceStr : lstSelPrice.getItems()) {
                total += Float.parseFloat(priceStr);
            }

            // Prepare SQL update query
            String query = "update customers set cname=?, emailid=?, address=?, dos=?, area=?, hawkerid=?, papers=?, prices=?, status=?, totalbill=? where mobile=?";
            PreparedStatement pst = con.prepareStatement(query);

            // Set parameters for update
            pst.setString(1, txtName.getText());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, txtAdd.getText());
            pst.setDate(4, java.sql.Date.valueOf(dtdos.getValue()));
            pst.setString(5, comboArea.getSelectionModel().getSelectedItem());
            pst.setString(6, comboHawker.getSelectionModel().getSelectedItem());
            pst.setString(7, papers);
            pst.setString(8, prices);
            pst.setInt(9, 1);
            pst.setFloat(10, total);
            pst.setString(11, txtMob.getText());

            int count = pst.executeUpdate();

            if (count > 0) {
                System.out.println("Updated");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        doNew(null);
    }


    @FXML
    void initialize()
    {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }
        ArrayList<String> AllPapers=getAllAPapers();
        lstPapers.getItems().addAll(AllPapers);
        ArrayList<String >Allprices=getAllNewspaperPrices();
        lstPrices.getItems().addAll(Allprices);
        ArrayList<String> Allareas=getAllAreas();
        comboArea.getItems().addAll(Allareas);
//        ArrayList<String>Avlhawkers=getAvlhawkers();
//        comboHawker.getItems().addAll(Avlhawkers);
    }
    ArrayList<String> getAllAPapers()
    {

        ArrayList<String> Papers=new ArrayList<String>();

        try
        {
            PreparedStatement stmt = con.prepareStatement("select * from newspapers");
            ResultSet res= stmt.executeQuery();

            while(res.next())
            {
                String paper=res.getString("paper")+" ("+res.getString("planguage")+")";
                Papers.add(paper);

            }
            System.out.println(Papers);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return Papers;


    }
    ArrayList<String> getAllNewspaperPrices() {
        ArrayList<String> priceList = new ArrayList<String>();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT price FROM newspapers");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                priceList.add(String.valueOf(rs.getFloat("price")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return priceList;
    }
/*
    ArrayList<String> getAvlhawkers() {
        ArrayList<String> avlhawkers = new ArrayList<String>();

        try {
            PreparedStatement pst = con.prepareStatement("select hawkerids from selarea like ?");
            String str= "%,"+comboArea.getSelectionModel().getSelectedItem()+",%";
            pst.setString(1,str);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                avlhawkers.add(String.valueOf(rs.getFloat("price")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return avlhawkers;
    }

*/
    ArrayList<String> getAllAreas() {
        ArrayList<String> areas = new ArrayList<String>();

        try {
            PreparedStatement pst = con.prepareStatement("select area from areas");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                areas.add(rs.getString("area"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areas;
    }

}
