/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
public class AssignedBusController implements Initializable {

    @FXML
    private ListView toCamp;
    @FXML
    private ListView fromCamp;

    ObservableList<String> obListToCamp = FXCollections.observableArrayList();
    ObservableList<String> obListFromCamp = FXCollections.observableArrayList();

    Session session;

    String selectedTripFromCampus;
    String selectedTripToCampus;

    @FXML
    private ComboBox<String> days;
    @FXML
    private VBox v;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (LoginController.student.getDriverId() == null) {

            label.setText("");
            v.getChildren().clear();
            //v2.setPrefHeight(150);

            ImageView no_driver = new ImageView(new Image("nothing-here.png"));
            no_driver.setPreserveRatio(true);
            no_driver.setFitWidth(300);

            Label no_driverLabel = new Label("You are not assigned to any bus");
            no_driverLabel.getStyleClass().add("gridpane-label");
            Button b = new Button();
            b.setText("Register in a Bus");
            b.setOnAction((ActionEvent e) -> {
                try {
                    changeBusb(e);
                } catch (IOException ex) {
                    Logger.getLogger(MissingItems_studentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            );

            v.getChildren().addAll(no_driver, no_driverLabel, b);
            v.setMargin(b, new Insets(15, 0, 0, 0));
            v.setMargin(no_driver, new Insets(0, 0, 0, 0));

        } else {

            //set time to be disabled untile the student choose a day
            toCamp.setDisable(true);
            fromCamp.setDisable(true);

            //days combo box
            ObservableList daysList = FXCollections.observableArrayList("sunday", "monday", "tuesday", "wednesday", "thursday");
            days.setItems(daysList);
            days.setValue("Choose a day");

            //get bus schedule from database
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            //1) get the driver
            Query query = session.createQuery("From Driver where driverId=" + LoginController.student.getDriverId());
            Driver driver = (Driver) query.uniqueResult();

//            query = session.createQuery("select s.start_time From Schedule_of_student s where s.studentId=" + LoginController.student.getStudentId());
//            List<String> trips_toCampusList = query.list();
//
//            query = session.createQuery("select s.end_time From Schedule_of_student s where s.studentId=" + LoginController.student.getStudentId());
//            List<String> trips_fromCampusList = query.list();
            //2) get schedule from busId
            query = session.createQuery("select t.toCampusTime From TripsSchedule_toCampus t where t.busId=" + driver.getBusId());
            List<String> trips_toCampusList = query.list();

            query = session.createQuery("select t.toCampusTime From TripsSchedule_toCampus t where t.busId=" + driver.getBusId());
            List<String> trips_fromCampusList = query.list();

            System.out.println(trips_fromCampusList.size());
            transaction.commit();
            session.close();

            //add the items to the lists 
            //1)
            obListToCamp.addAll(trips_toCampusList);
            obListFromCamp.addAll(trips_fromCampusList);

            //2)
            toCamp.setItems(obListToCamp);
            fromCamp.setItems(obListFromCamp);

            //add an event listener to get selected item
            toCamp.getSelectionModel().selectedItemProperty().addListener(e -> {
                selectedTripToCampus = toCamp.getSelectionModel().getSelectedItem().toString();
            });

            fromCamp.getSelectionModel().selectedItemProperty().addListener(e -> {
                selectedTripFromCampus = fromCamp.getSelectionModel().getSelectedItem().toString();
            });
        }
    }

    @FXML
    private void cancelAtripSwitch(ActionEvent event) {
        if (!toCamp.isDisable() && selectedTripFromCampus == null && selectedTripToCampus == null) {
            Alert fillTheFields = new Alert(Alert.AlertType.WARNING);
            fillTheFields.setTitle("Warning");
            fillTheFields.setHeaderText(null);
            fillTheFields.setContentText("Choose a trip to cancel");
            fillTheFields.showAndWait();

        } else if (days.getValue().equals("Choose a day")) {
            Alert fillTheFields = new Alert(Alert.AlertType.WARNING);
            fillTheFields.setTitle("Warning");
            fillTheFields.setHeaderText(null);
            fillTheFields.setContentText("Choose a day to cancel");
            fillTheFields.showAndWait();

        } else if (selectedTripToCampus != null) {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Schedule_of_student where day_of_week = " + "\'" + days.getValue() + "\' and start_time = \'" + selectedTripToCampus + "\'");
            Schedule_of_student todaySchedule = (Schedule_of_student) query.uniqueResult();

            if (todaySchedule == null) {
                Alert fillTheFields = new Alert(Alert.AlertType.WARNING);
                fillTheFields.setTitle("Warning");
                fillTheFields.setHeaderText(null);
                fillTheFields.setContentText("No such trip");
                fillTheFields.showAndWait();
            } else {
                todaySchedule.setStart_state("not ready");
            }
            transaction.commit();
            session.close();
        }
        if (selectedTripFromCampus != null) {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Schedule_of_student where day_of_week = " + "\'" + days.getValue() + "\' and end_time = \'" + selectedTripFromCampus + "\'");
            Schedule_of_student todaySchedule = (Schedule_of_student) query.uniqueResult();

            if (todaySchedule == null) {
                Alert fillTheFields = new Alert(Alert.AlertType.WARNING);
                fillTheFields.setTitle("Warning");
                fillTheFields.setHeaderText(null);
                fillTheFields.setContentText("No such trip");
                fillTheFields.showAndWait();
            } else {
                todaySchedule.setEnd_state("not ready");
            }
            transaction.commit();
            session.close();
        }
    }

    //go to next page when click on view driver information button
    @FXML
    private void viewDriverInformation(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("assignedBus_driverInformation.fxml"));
        Scene nextpage = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(nextpage);
        stage.show();
    }

    //go to next page when click on change bus button
    private void changeBusb(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("changeBus.fxml"));
        Scene nextpage = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(nextpage);
        stage.show();
    }

    //////////////// navigation bar ///////////////////
    @FXML
    private void homepage(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("homePage_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void myprofile(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("student_myProfile.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void notifications(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("notification_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void assignedBus(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("assignedBus.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void missingItems(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void showTimes(ActionEvent event) {

        toCamp.setDisable(false);
        fromCamp.setDisable(false);

    }

}
