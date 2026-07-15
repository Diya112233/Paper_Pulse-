package com.example.project_news.customersboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.project_news.jdbcc.MysqlDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomersBoardController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private Button btnGetCustomers;
    @FXML
    private ComboBox<String> comboarea;
    @FXML
    private ComboBox<String> combohawker;
    @FXML
    private ComboBox<String> combopaper;
    @FXML
    private TableView<CustomerBean> tblCustomers;

    Connection con;

    @FXML
    void initialize() {
        con = MysqlDBConnection.getMySQLDBConnection();
        if (con == null) {
            System.out.println("Connection Error");
        }

        comboarea.getItems().addAll(getAllAreas());
        combohawker.getItems().addAll(getAllHawkers());
        combopaper.getItems().addAll(getAllPapers());

        TableColumn<CustomerBean, String> mobileC = new TableColumn<>("Mobile No.");
        mobileC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobileC.setMinWidth(80);

        TableColumn<CustomerBean, String> nameC = new TableColumn<>("Name");
        nameC.setCellValueFactory(new PropertyValueFactory<>("cname"));
        nameC.setMinWidth(80);

        TableColumn<CustomerBean, String> addressC = new TableColumn<>("Address");
        addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressC.setMinWidth(200);

        TableColumn<CustomerBean, String> areaC = new TableColumn<>("Area");
        areaC.setCellValueFactory(new PropertyValueFactory<>("area"));
        areaC.setMinWidth(100);

        TableColumn<CustomerBean, String> hawkeridC = new TableColumn<>("Hawker ID");
        hawkeridC.setCellValueFactory(new PropertyValueFactory<>("hawkerid"));
        hawkeridC.setMinWidth(120);

        TableColumn<CustomerBean, String> papersC = new TableColumn<>("Newspapers");
        papersC.setCellValueFactory(new PropertyValueFactory<>("papers"));
        papersC.setMinWidth(160);

        tblCustomers.getColumns().addAll(mobileC, nameC, addressC, areaC, hawkeridC, papersC);
    }

    @FXML
    void doShowCustomers(ActionEvent event) {
        String selectedArea = comboarea.getValue();
        String selectedHawker = combohawker.getValue();
        String selectedPaper = combopaper.getValue();

        StringBuilder query = new StringBuilder("SELECT * FROM customers WHERE status = 1");
        ArrayList<String> params = new ArrayList<>();

        if (selectedArea != null && !selectedArea.trim().isEmpty()) {
            query.append(" AND area = ?");
            params.add(selectedArea);
        }

        if (selectedHawker != null && !selectedHawker.trim().isEmpty()) {
            query.append(" AND hawkerid = ?");
            params.add(selectedHawker);
        }

        if (selectedPaper != null && !selectedPaper.trim().isEmpty()) {

            query.append(" AND papers like ?");
            params.add("%" + selectedPaper + "%");

        }

        try {
            PreparedStatement pst = con.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                pst.setString(i + 1, params.get(i));
            }

            ResultSet rs = pst.executeQuery();
            tblCustomers.getItems().clear();

            while (rs.next()) {
                CustomerBean cb = new CustomerBean(
                        rs.getString("mobile"),
                        rs.getString("cname"),
                        rs.getString("address"),
                        rs.getString("area"),
                        rs.getString("hawkerid"),
                        rs.getString("papers")
                );
                tblCustomers.getItems().add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<String> getAllAreas() {
        ArrayList<String> areas = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT area FROM areas");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                areas.add(rs.getString("area"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areas;
    }

    ArrayList<String> getAllHawkers() {
        ArrayList<String> hawkers = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT hawkerid FROM hawkers");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                hawkers.add(rs.getString("hawkerid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hawkers;
    }

    ArrayList<String> getAllPapers() {
        ArrayList<String> papers = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT paper, planguage FROM newspapers");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String display =  rs.getString("paper") + " (" + rs.getString("planguage") + ")";
                papers.add(display);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return papers;
    }
}
