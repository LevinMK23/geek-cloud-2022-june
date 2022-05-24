module com.geekbrains.cloud.june.cloudapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.geekbrains.cloud.june.cloudapplication to javafx.fxml;
    exports com.geekbrains.cloud.june.cloudapplication;
}