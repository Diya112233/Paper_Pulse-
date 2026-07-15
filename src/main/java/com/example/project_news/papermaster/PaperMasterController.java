package com.example.project_news.papermaster;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PaperMasterController {

    @FXML
    private ResourceBundle resources;

    Connection con=null;

    @FXML
    private URL location;

    @FXML
    private ImageView imgprev;

    @FXML
    private TextField txtLang;

    @FXML
    private ComboBox<String> comboPaperTitle;

    @FXML
    private TextField txtPrice;

    @FXML
    void doDelete(ActionEvent event) {
        String query="delete from newspapers where paper=? and planguage=?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,comboPaperTitle.getValue());
            pst.setString(2,txtLang.getText());
            int cnt= pst.executeUpdate();
            if(cnt==0)
                System.out.println("Inavlid details");
            else
                System.out.println("Deleted");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        doNew(null);

    }



    @FXML
    void doNew(ActionEvent event) {
        comboPaperTitle.getSelectionModel().select("select");
        txtLang.clear();
        txtPrice.clear();

    }

    @FXML
    void doSave(ActionEvent event) {
        String query="insert into newspapers values(?,?,?)";
        try
        {
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,comboPaperTitle.getValue());
            pst.setString(2,txtLang.getText());
            pst.setFloat(3,Float.parseFloat(txtPrice.getText()));
            pst.executeUpdate();
            System.out.println("saved");
            doNew(null);

        }
        catch (Exception exp)
        {
            exp.getStackTrace();
        }

    }

    @FXML
    void doUpdate(ActionEvent event) {
        String query="update newspapers set price=? where paper=? and planguage=?";
        try
        {
            PreparedStatement pst= con.prepareStatement(query);

            pst.setFloat(1,Float.parseFloat(txtPrice.getText()));
            pst.setString(2,comboPaperTitle.getValue());
            pst.setString(3,txtLang.getText());
            int chk=pst.executeUpdate();
            if(chk!=0)
                System.out.println("Updated");
            else System.out.println("Invalid id");

        }
        catch (Exception exp)
        {
            exp.toString();
        }
        doNew(null);

    }

    @FXML
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null) {
            System.out.println("Connection Error");
            return;
        }
       try {
           imgprev.setImage(new Image(getClass().getResourceAsStream("/com/example/project_news/papermasterr/Newspaper.png")));

       }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        comboPaperTitle.getItems().add("Select");
        ArrayList<String>allpapers=getpapers();
        comboPaperTitle.getItems().addAll(allpapers);
        comboPaperTitle.setOnAction(event -> checkAndFetchPrice());
        txtLang.setOnKeyReleased(event -> checkAndFetchPrice());

    }
    ArrayList<String> getpapers()
    {
        ArrayList<String> allpapers=new ArrayList<String>();
        try{
            PreparedStatement pst=con.prepareStatement("select distinct paper from newspapers");
            ResultSet res=pst.executeQuery();

            while(res.next())
            {
                String paper= res.getString("paper");
                allpapers.add(String.valueOf(paper));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  allpapers;
    }

    void checkAndFetchPrice()
    {
        String title = comboPaperTitle.getValue();
        String lang = txtLang.getText();
        float paperprice = 0;
        if (title != null && !title.isEmpty() && lang != null && !lang.isEmpty())
        {

            String query= "Select price from newspapers where paper=? and planguage=?";
            try {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,title);
                pst.setString(2,lang);
                ResultSet paperpricearr=pst.executeQuery();
                while(paperpricearr.next())
                {
                     paperprice = paperpricearr.getFloat("price");
                    txtPrice.setText(String.valueOf(paperprice));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        else {
            txtPrice.clear();
        }
    }



}

