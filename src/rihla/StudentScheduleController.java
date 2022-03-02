/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class StudentScheduleController implements Initializable {

    @FXML
    private ComboBox<String> sundayStart;
    @FXML
    private ComboBox<String> sundayEnd;
    @FXML
    private ComboBox<String> mondayStart;
    @FXML
    private ComboBox<String> mondayEnd;
    @FXML
    private ComboBox<String> tuesdayStart;
    @FXML
    private ComboBox<String> tuesdayEnd;
    @FXML
    private ComboBox<String> wednesdayStart;
    @FXML
    private ComboBox<String> wednesdayEnd;
    @FXML
    private ComboBox<String> thursdayStart;
    @FXML
    private ComboBox<String> thursdayEnd;

    protected static List<Schedule_of_student> schedule_of_student = new ArrayList<>();

    int daysCount = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList times_from_campus_List = FXCollections.observableArrayList("09:00AM", "10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "3:00PM", "04:00PM", "05:00PM", "06:00PM");

        ObservableList times_to_campus_List = FXCollections.observableArrayList("07:00AM", "08:00AM", "09:00AM", "10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "3:00PM");

        sundayStart.setItems(times_to_campus_List);
        sundayEnd.setItems(times_from_campus_List);
        sundayStart.setValue("Time");
        sundayEnd.setValue("Time");

        mondayStart.setItems(times_to_campus_List);
        mondayEnd.setItems(times_from_campus_List);
        mondayStart.setValue("Time");
        mondayEnd.setValue("Time");

        tuesdayStart.setItems(times_to_campus_List);
        tuesdayEnd.setItems(times_from_campus_List);
        tuesdayStart.setValue("Time");
        tuesdayEnd.setValue("Time");

        wednesdayStart.setItems(times_to_campus_List);
        wednesdayEnd.setItems(times_from_campus_List);
        wednesdayStart.setValue("Time");
        wednesdayEnd.setValue("Time");

        thursdayStart.setItems(times_to_campus_List);
        thursdayEnd.setItems(times_from_campus_List);
        thursdayStart.setValue("Time");
        thursdayEnd.setValue("Time");

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("createStudentAccount.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void goForward(ActionEvent event) throws IOException {

        if (!sundayStart.getValue().equals("Time") && !sundayEnd.getValue().equals("Time")) {
            schedule_of_student.add(new Schedule_of_student("sunday", sundayStart.getValue(), sundayEnd.getValue(), "ready", "ready"));
            daysCount += 1;
        }
        if (!mondayStart.getValue().equals("Time") && !mondayEnd.getValue().equals("Time")) {
            schedule_of_student.add(new Schedule_of_student("monday", mondayStart.getValue(), mondayEnd.getValue(), "ready", "ready"));
            daysCount += 1;
        }
        if (!tuesdayStart.getValue().equals("Time") && !tuesdayEnd.getValue().equals("Time")) {
            schedule_of_student.add(new Schedule_of_student("tuesday", tuesdayStart.getValue(), tuesdayEnd.getValue(), "ready", "ready"));
            daysCount += 1;
        }
        if (!wednesdayStart.getValue().equals("Time") && !wednesdayEnd.getValue().equals("Time")) {
            schedule_of_student.add(new Schedule_of_student("wednesday", wednesdayStart.getValue(), wednesdayEnd.getValue(), "ready", "ready"));
            daysCount += 1;
        }
        if (!thursdayStart.getValue().equals("Time") && !thursdayEnd.getValue().equals("Time")) {
            schedule_of_student.add(new Schedule_of_student("thursday", thursdayStart.getValue(), thursdayEnd.getValue(), "ready", "ready"));
            daysCount += 1;
        }

        if (daysCount < 1) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill in at least one day");
            error.show();
        } else {
            Parent nextPage = FXMLLoader.load(getClass().getResource("availableBuses.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }
    }

}
