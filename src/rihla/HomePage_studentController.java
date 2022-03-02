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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class HomePage_studentController implements Initializable {

    ObservableList<String> obListToCamp = FXCollections.observableArrayList();
    ObservableList<String> obListFromCamp = FXCollections.observableArrayList();

    Session session;

    String selectedTripFromCampus;
    String selectedTripToCampus;
    @FXML
    private GridPane schedule;
    @FXML
    private Text name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int space = LoginController.student.getName().indexOf(" ");
        String name2 = LoginController.student.getName().substring(0, space);
        name.setText(name2);

        session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Schedule_of_student s where s.studentId=" + LoginController.student.getStudentId());
        List<Schedule_of_student> sche = query.list();

        System.out.println(sche.size());
        transaction.commit();
        session.close();

        int sun = 0;
        int mon = 0;
        int tues = 0;
        int wed = 0;
        int thurs = 0;

        for (Schedule_of_student s : sche) {

            Label label = new Label();
            Label label2 = new Label();

            switch (s.getDay_of_week().toLowerCase()) {
                case "sunday": {
                    label.setText(s.getStart_time());
                    schedule.add(label, 1, 0);
                    label2.setText(s.getEnd_time());
                    schedule.add(label2, 2, 0);
                    sun++;
                    break;
                }

                case "monday": {
                    label.setText(s.getStart_time());
                    schedule.add(label, 1, 1);
                    label2.setText(s.getEnd_time());
                    schedule.add(label2, 2, 1);
                    mon++;
                    break;
                }

                case "tuesday": {
                    label.setText(s.getStart_time());
                    schedule.add(label, 1, 2);
                    label2.setText(s.getEnd_time());
                    schedule.add(label2, 2, 2);
                    tues++;
                    break;
                }

                case "wednesday": {
                    label.setText(s.getStart_time());
                    schedule.add(label, 1, 3);
                    label2.setText(s.getEnd_time());
                    schedule.add(label2, 2, 3);
                    wed++;
                    break;
                }

                case "thursday": {
                    label.setText(s.getStart_time());
                    schedule.add(label, 1, 4);
                    label2.setText(s.getEnd_time());
                    schedule.add(label2, 2, 4);
                    thurs++;
                    break;
                }

                default: {
                    break;
                }

            }

        }

        if (sun == 0) {
            schedule.add(new Label("-"), 1, 0);
            schedule.add(new Label("-"), 2, 0);
        }
        if (mon == 0) {
            schedule.add(new Label("-"), 1, 1);
            schedule.add(new Label("-"), 2, 1);
        }
        if (tues == 0) {
            schedule.add(new Label("-"), 1, 2);
            schedule.add(new Label("-"), 2, 2);
        }
        if (wed == 0) {
            schedule.add(new Label("-"), 1, 3);
            schedule.add(new Label("-"), 2, 3);
        }
        if (thurs == 0) {
            schedule.add(new Label("-"), 1, 4);
            schedule.add(new Label("-"), 2, 4);
        }

    }

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
}
