package com.example.project_news.hawkers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.sql.*;

public class HawkersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboAllareas;

    @FXML
    private ComboBox<String> combohawkerId;

    @FXML
    private DatePicker dtjoining;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imgprev;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAdhaar;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSelectedArea;

    String picpath;
    @FXML
    void doBrowse(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.jpg","*.png") );
        File file= chooser.showOpenDialog(null);
        picpath = file.getAbsolutePath();

        try {

            imgprev.setImage(new Image(new FileInputStream(file)));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void doClear(ActionEvent event) {
        txtAddress.clear();
        txtAdhaar.clear();
        txtContact.clear();
        txtName.clear();
        txtSelectedArea.clear();
        comboAllareas.getSelectionModel().select("Select");
        combohawkerId.getSelectionModel().select("Select");
        try {
            imgprev.setImage(new Image(new FileInputStream("C:/Users/NEW/OneDrive/Pictures/Screenshots/Screenshot 2025-07-12 121257.png")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void doDelete(ActionEvent event) {
        String query="delete from hawkers where hawkerid=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,combohawkerId.getSelectionModel().getSelectedItem());
            pst.executeUpdate();
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Deleted");
        doClear(null);

    }

    @FXML
    void doFetch(ActionEvent event) {
        String query="select * from hawkers where hawkerid=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,combohawkerId.getSelectionModel().getSelectedItem());
            ResultSet arr= pst.executeQuery();
            while(arr.next())
            {
                txtName.setText(arr.getString("hname"));
                txtContact.setText(arr.getString("contact"));
                txtAddress.setText(arr.getString("address"));
                txtAdhaar.setText(arr.getString("adhaar"));
                txtSelectedArea.setText(arr.getString("selareas"));
                dtjoining.setValue(arr.getDate("doj").toLocalDate());
                imgprev.setImage(new Image(new FileInputStream(arr.getString("picpath"))));


            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }


    @FXML
    void doSave(ActionEvent event) {
        try {
            String name = txtName.getText();
            String contact = txtContact.getText();
            String address = txtAddress.getText();
            String adhaar = txtAdhaar.getText();
            Date doj = java.sql.Date.valueOf(dtjoining.getValue());
            String selareas = txtSelectedArea.getText();

            String hawkerId = name.substring(0, 3).toUpperCase() + contact.substring(contact.length() - 5);

            String query = "insert into hawkers (hawkerid, hname, contact, address, adhaar, doj, picpath, selareas) values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, hawkerId);
            pst.setString(2, name);
            pst.setString(3, contact);
            pst.setString(4, address);
            pst.setString(5, adhaar);
            pst.setDate(6, doj);
            pst.setString(7, picpath);
            pst.setString(8, selareas);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("saved");
        doClear(null);
    }







    @FXML
    void doUpdate(ActionEvent event) {
        try {
           String hawkerId=combohawkerId.getSelectionModel().getSelectedItem();
            if (hawkerId == null) {
                return;
            }

            String query = "update hawkers set hname=?, contact=?, address=?, adhaar=?, doj=?, picpath=?, selareas=? where hawkerid=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtName.getText());
            pst.setString(2, txtContact.getText());
            pst.setString(3, txtAddress.getText());
            pst.setString(4, txtAdhaar.getText());
            pst.setDate(5, java.sql.Date.valueOf(dtjoining.getValue()));
            pst.setString(6, picpath);
            pst.setString(7, txtSelectedArea.getText());
            pst.setString(8, combohawkerId.getSelectionModel().getSelectedItem());

            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Updated");
            else
                System.out.println("No record updated. Check Hawker ID.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        doClear(null);
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

        try {
            imgprev.setImage(new Image(new FileInputStream("C:/Users/NEW/OneDrive/Pictures/Screenshots/Screenshot 2025-07-12 121257.png")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ArrayList<String> hids=gethids();
        for( String str:hids)
        {
            combohawkerId.getItems().add((str));
        }

        ArrayList<String> Allareas=getAllAreas();
        comboAllareas.getItems().addAll(Allareas);


        comboAllareas.setOnAction(e -> {
            String selected = comboAllareas.getValue();
            String current = txtSelectedArea.getText();

            if (!current.contains(selected)) {
                if (current.isEmpty()) {
                    txtSelectedArea.setText(selected);
                } else {
                    txtSelectedArea.setText(current + "," + selected);
                }
            }
        });

    }
    ArrayList<String> gethids()
    {
        ArrayList<String> hids=new ArrayList<String>();
        try{
            PreparedStatement pst=con.prepareStatement("select hawkerid from hawkers");
            ResultSet res=pst.executeQuery();

            while(res.next())
            {
                String hid= res.getString("hawkerid");
                hids.add(hid);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  hids;
    }
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
