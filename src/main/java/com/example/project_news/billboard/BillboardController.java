package com.example.project_news.billboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.project_news.billcollect.BillingBean;
import com.example.project_news.jdbcc.MysqlDBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combostatus;

    @FXML
    private DatePicker dtfrom;

    @FXML
    private DatePicker dtto;

    @FXML
    private TableView<BillingBean> tblallbills;

    @FXML
    private TextField txtAmount;

    Connection con = null;

    @FXML
    void initialize() {
        con = MysqlDBConnection.getMySQLDBConnection();
        if (con == null) {
            System.out.println("Connection Error");
        }

        String[] cmb = { "All", "Paid", "Pending" };
        combostatus.getItems().addAll(cmb);
        combostatus.getSelectionModel().select(0); // Default to "All"
    }

    @FXML
    void doGetAllBills(ActionEvent event) {
        tblallbills.getColumns().clear();

        TableColumn<BillingBean, String> dtfromC = new TableColumn<>("From date:");
        dtfromC.setCellValueFactory(new PropertyValueFactory<>("dos"));
        dtfromC.setMinWidth(100);

        TableColumn<BillingBean, String> dttoC = new TableColumn<>("To date:");
        dttoC.setCellValueFactory(new PropertyValueFactory<>("doe"));
        dttoC.setMinWidth(100);

        TableColumn<BillingBean, String> daysC = new TableColumn<>("No. of days:");
        daysC.setCellValueFactory(new PropertyValueFactory<>("days"));
        daysC.setMinWidth(100);

        TableColumn<BillingBean, String> lessdaysC = new TableColumn<>("Less days");
        lessdaysC.setCellValueFactory(new PropertyValueFactory<>("lessdays"));
        lessdaysC.setMinWidth(100);

        TableColumn<BillingBean, String> billC = new TableColumn<>("Total Bill");
        billC.setCellValueFactory(new PropertyValueFactory<>("bill"));
        billC.setMinWidth(100);


        TableColumn<BillingBean, String> statusC = new TableColumn<>("Status");
        statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusC.setMinWidth(100);

        tblallbills.getColumns().addAll(dtfromC, dttoC, daysC, lessdaysC, billC, statusC);
        tblallbills.setItems(null);
        tblallbills.setItems(getFilteredBills());
    }

    ObservableList<BillingBean> getFilteredBills() {
        ObservableList<BillingBean> arr = FXCollections.observableArrayList();
        try {
            // Always start with date filtering
            StringBuilder baseQuery = new StringBuilder("SELECT * FROM billing WHERE dos >= ? AND doe <= ?");

            int selectedIndex = combostatus.getSelectionModel().getSelectedIndex();
            boolean useStatus = (selectedIndex != 0); // Not "All"

            if (useStatus) {
                baseQuery.append(" AND status = ?");
            }

            PreparedStatement pst = con.prepareStatement(baseQuery.toString());

            // Set date values (always required)
            pst.setDate(1, java.sql.Date.valueOf(dtfrom.getValue()));
            pst.setDate(2, java.sql.Date.valueOf(dtto.getValue()));

            if (useStatus) {
                int statusValue = (selectedIndex == 1) ? 0 : 1; // Paid or Pending
                pst.setInt(3, statusValue);
            }

            ResultSet res = pst.executeQuery();
            float amount = 0;

            while (res.next()) {
                String dos = String.valueOf(res.getDate("dos"));
                String doe = String.valueOf(res.getDate("doe"));
                String lessdays = String.valueOf(res.getInt("days"));
                String bill = String.valueOf(res.getFloat("bill"));

                long diffMillis = res.getDate("doe").getTime() - res.getDate("dos").getTime();
                long totalDays = (diffMillis / (1000 * 60 * 60 * 24)) + 1;
                String days = String.valueOf(totalDays);

                amount += res.getFloat("bill");

                int statusValue = res.getInt("status");
                String statusText = (statusValue == 0) ? "Paid" : "Pending";

                BillingBean blt = new BillingBean(dos, doe, days, lessdays, bill, statusText);
                arr.add(blt);
            }

            txtAmount.setText(String.format("%.2f", amount));

            if (arr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Results");
                alert.setHeaderText(null);
                alert.setContentText("No records found for the selected filters.");
                alert.showAndWait();
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return arr;
    }


}
