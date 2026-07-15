package com.example.project_news.dashboard;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.project_news.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imglogo;

    @FXML
    void openAbout(ActionEvent event) {

    }

    @FXML
    void openAnalyzer(ActionEvent event) {

        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(""));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openAreaMaster(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("areass/AreasView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openBillController(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billcollectt/BillCollectorView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openCustomerBoard(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customersboardd/CustomersBoardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openCustomerEnrollment(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customerss/CustomersView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openGenerateBills(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billingg/BillingView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openGetAllBills(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("billboardd/BillBoardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openHawkerMaster(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hawkerss/HawkersView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openHawkersBoard(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hawkerboardd/HawkerBoardView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openNewspapersBoard(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newspapertablee/newspapertableView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openPaperMaster(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("papermasterr/PaperMasterView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            //Scene scene1=(Scene)btnComboApp.getScene();
            //scene1.getWindow().hide();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            imglogo.setImage(new Image(new FileInputStream("C:/Users/NEW/Downloads/Gemini_Generated_Image_domcwdomcwdomcwd.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
