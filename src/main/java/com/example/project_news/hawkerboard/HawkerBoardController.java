package com.example.project_news.hawkerboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.project_news.hawkerboard.HawkerBean;
import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HawkerBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnGetHawkers;

    @FXML
    private ComboBox<String> comboarea;

    @FXML
    private TableView<HawkerBean> tblHawkers;

    @FXML
    void doShowCustomers(ActionEvent event) {

        String query="select * from hawkers where selareas like ?";
        try{
            PreparedStatement pst= con.prepareStatement(query);
            pst.setString(1,"%"+comboarea.getSelectionModel().getSelectedItem()+"%");
            ResultSet rs=pst.executeQuery();

            tblHawkers.getItems().clear();

            while(rs.next())
            {
                HawkerBean hkr=new HawkerBean(rs.getString("hawkerid"),rs.getString("hname"),rs.getString("contact"),rs.getString("address"),rs.getString("selareas"));

                tblHawkers.getItems().add(hkr);
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        

    }
    Connection con=null;
    @FXML
    void initialize() {
        con = MysqlDBConnection.getMySQLDBConnection();
        if (con == null) {
            System.out.println("Connection Error");
        }

        comboarea.getItems().addAll(getAllAreas());

        TableColumn<HawkerBean, String> hawkeridC = new TableColumn<>("Hawker Id");
        hawkeridC.setCellValueFactory(new PropertyValueFactory<>("hawkerid"));
        hawkeridC.setMinWidth(100);
        
        TableColumn<HawkerBean, String> nameC = new TableColumn<>("Name");
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameC.setMinWidth(100);

        TableColumn<HawkerBean, String> contactC = new TableColumn<>("Contact");
        contactC.setCellValueFactory(new PropertyValueFactory<>("contact"));
        contactC.setMinWidth(100);

        TableColumn<HawkerBean, String> addressC = new TableColumn<>("Address");
        addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressC.setMinWidth(100);

        TableColumn<HawkerBean, String> selareasC = new TableColumn<>("Selected Areas");
        selareasC.setCellValueFactory(new PropertyValueFactory<>("selareas"));
        selareasC.setMinWidth(100);

        tblHawkers.getColumns().addAll(hawkeridC,nameC,contactC,addressC,selareasC);

        

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
