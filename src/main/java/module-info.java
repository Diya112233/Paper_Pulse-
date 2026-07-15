module com.example.project_news {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.project_news to javafx.fxml;
    exports com.example.project_news;

    opens com.example.project_news.papermaster to javafx.fxml;
    exports com.example.project_news.papermaster;

    opens com.example.project_news.customers to javafx.fxml;
    exports com.example.project_news.customers;

    opens com.example.project_news.areas to javafx.fxml;
    exports com.example.project_news.areas;

    opens com.example.project_news.billing to javafx.fxml;
    exports com.example.project_news.billing;

    opens com.example.project_news.hawkers to javafx.fxml;
    exports com.example.project_news.hawkers;

    opens com.example.project_news.newspapertable to javafx.fxml;
    exports com.example.project_news.newspapertable;

    opens com.example.project_news.billcollect to javafx.fxml;
    exports com.example.project_news.billcollect;

    opens com.example.project_news.billboard to javafx.fxml;
    exports com.example.project_news.billboard;

    opens com.example.project_news.customersboard to javafx.fxml;
    exports com.example.project_news.customersboard;

    opens com.example.project_news.hawkerboard to javafx.fxml;
    exports com.example.project_news.hawkerboard;

    opens com.example.project_news.dashboard to javafx.fxml;
    exports com.example.project_news.dashboard;
}
