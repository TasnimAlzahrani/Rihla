///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rihla;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.ComboBox;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author HU6EM001
// */
//public class Notification_driverController implements Initializable {
//
//    @FXML
//    private ComboBox<String> notifications_comboBox;
//
//    private ObservableList notificationsList;
//    @FXML
//    private ComboBox<String> approx_time_comboBox;
//
//    private ObservableList approx_timeList;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        notificationsList = FXCollections.observableArrayList("There will a delay because of demage", "There will be a delay because of traffic", "The bus driver won't be able to pick you up today");
//        approx_timeList = FXCollections.observableArrayList("5 minutes", "10 minutes", "20 minutes", "30 minutes", "One hour", "Two hours", "-");
//
//        notifications_comboBox.setItems(notificationsList);
//        notifications_comboBox.setValue("The message");
//
//        approx_time_comboBox.setItems(approx_timeList);
//        approx_time_comboBox.setValue("The approx. time");
//    }
//
//    @FXML
//    private void homepage(ActionEvent event) throws IOException {
//
//        Parent forgetpassword = FXMLLoader.load(getClass().getResource("homePage_driver.fxml"));
//        Scene forgetpasswordscene = new Scene(forgetpassword);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(forgetpasswordscene);
//        stage.show();
//    }
//
//    @FXML
//    private void myProfile(ActionEvent event) throws IOException {
//
//        Parent forgetpassword = FXMLLoader.load(getClass().getResource("driver_myProfile.fxml"));
//        Scene forgetpasswordscene = new Scene(forgetpassword);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(forgetpasswordscene);
//        stage.show();
//    }
//
//    @FXML
//    private void notification(ActionEvent event) throws IOException {
//
//        Parent forgetpassword = FXMLLoader.load(getClass().getResource("notification_driver.fxml"));
//        Scene forgetpasswordscene = new Scene(forgetpassword);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(forgetpasswordscene);
//        stage.show();
//    }
//
//    @FXML
//    private void studentsList(ActionEvent event) throws IOException {
//        Parent forgetpassword = FXMLLoader.load(getClass().getResource("driver_studentsList.fxml"));
//        Scene forgetpasswordscene = new Scene(forgetpassword);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(forgetpasswordscene);
//        stage.show();
//    }
//
//    @FXML
//    private void missingItems(ActionEvent event) throws IOException {
//        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_driver.fxml"));
//        Scene forgetpasswordscene = new Scene(forgetpassword);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(forgetpasswordscene);
//        stage.show();
//    }
//
//    @FXML
//    private void send_notofication(ActionEvent event) {
//        
//
//    }
//
//}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class Notification_driverController implements Initializable {

    @FXML
    private ComboBox<String> notifications_comboBox;

    private ObservableList notificationsList;
    @FXML
    private ComboBox<String> approx_time_comboBox;

    private ObservableList approx_timeList;

    private Label notification1;

    Notification i = new Notification();

    @FXML
    private VBox container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        notificationsList = FXCollections.observableArrayList("There will a delay because of demage", "There will be a delay because of traffic", "The bus driver won't be able to pick you up today");
        approx_timeList = FXCollections.observableArrayList("5 minutes", "10 minutes", "20 minutes", "30 minutes", "One hour", "Two hours", "-");

        notifications_comboBox.setItems(notificationsList);
        notifications_comboBox.setValue("The message");

        approx_time_comboBox.setItems(approx_timeList);
        approx_time_comboBox.setValue("The approx. time");
    }

    @FXML
    private void homepage(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("homePage_driver.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void myProfile(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("driver_myProfile.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void notification(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("notification_driver.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void studentsList(ActionEvent event) throws IOException {
        Parent forgetpassword = FXMLLoader.load(getClass().getResource("driver_studentsList.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void missingItems(ActionEvent event) throws IOException {
        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_driver.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void send_notofication(ActionEvent event) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        i.setNotificationcontent((String) notifications_comboBox.getValue());
        i.setApproximatedTime((String) approx_time_comboBox.getValue());
        i.setDriverId(LoginController.driver.getDriverId());
        session.save(i);

        transaction.commit();
        session.close();

    }

}
