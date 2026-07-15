
package com.example.project_news.newspapertable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class newspapertableController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tblnewspapers"
    private TableView<NewspaperBean> tblnewspapers; // Value injected by FXMLLoader

    @FXML
    void doExportToExcel(ActionEvent event) {

    }

    @FXML
    void doGetAll(ActionEvent event) {
        TableColumn<NewspaperBean, String> paperC=new TableColumn<NewspaperBean, String>("Newspaper Title");
        paperC.setCellValueFactory(new PropertyValueFactory<NewspaperBean,String>("paper"));
        paperC.setMinWidth(100);

        TableColumn<NewspaperBean, String> langC=new TableColumn<NewspaperBean, String>("Language");
        langC.setCellValueFactory(new PropertyValueFactory<NewspaperBean,String>("language"));
        langC.setMinWidth(100);

        TableColumn<NewspaperBean, String> priceC=new TableColumn<NewspaperBean, String>("Price");
        priceC.setCellValueFactory(new PropertyValueFactory<NewspaperBean,String>("price"));
        priceC.setMinWidth(100);


        tblnewspapers.getColumns().addAll(paperC,langC,priceC);
        tblnewspapers.setItems(null);
        tblnewspapers.setItems(getArrayOfNewspapers());


    }
Connection con=null;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        con= MysqlDBConnection.getMySQLDBConnection();
        if(con==null)
        {
            System.out.println("Connection Error");
            return;
        }

    }

    ObservableList<NewspaperBean> getArrayOfNewspapers()
    {
        ObservableList<NewspaperBean> arr = FXCollections.observableArrayList();
        try{
            String query="select * from newspapers";
            PreparedStatement pst=con.prepareStatement(query);
            ResultSet res= pst.executeQuery();

            while(res.next())
            {
                String paper=res.getString("paper");
                String lang=res.getString("planguage");
                String price= String.valueOf(res.getFloat("price"));

                NewspaperBean npr= new NewspaperBean(paper,lang,price);
                arr.add(npr);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }

}
