/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
public class Driver_studentsListController implements Initializable {

    @FXML
    private VBox container;
    private List<TripsSchedule_fromCampus> tripsSchedule_fromCampus;
    private List<TripsSchedule_toCampus> tripsSchedule_toCampus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From Student where driverId=" + LoginController.driver.getDriverId());

        List<Student> list_of_student = query.list();

        transaction.commit();
        session.close();

        container.setSpacing(20);
        // TODO
        if (!list_of_student.isEmpty()) {
            for (Student student : list_of_student) {

                Label name = new Label(student.getName());
                name.getStyleClass().add("gridpane-label");

                GridPane info = new GridPane();
                info.setHgap(30);
                info.setVgap(3);

                ColumnConstraints column1 = new ColumnConstraints();
                column1.setHalignment(HPos.LEFT);
                info.getColumnConstraints().add(column1);
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setHalignment(HPos.LEFT);
                info.getColumnConstraints().add(column2);

                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();

                query = session.createQuery("From StudentAddress where zipcode=" + student.getZipcode());
                StudentAddress studentAddress = (StudentAddress) query.uniqueResult();

                query = session.createQuery("From Schedule_of_student where studentId=" + student.getStudentId() + " and day_of_week = \'monday\'");
                Schedule_of_student schedule_of_student = (Schedule_of_student) query.uniqueResult();

                transaction.commit();
                session.close();

                if (schedule_of_student == null) {
                    continue;
                }

                Label readyFromHome = new Label(schedule_of_student.getStart_state());
                if ((readyFromHome.getText()).equals("ready")) {
                    readyFromHome.getStyleClass().add("readygreen");
                } else if ((readyFromHome.getText()).equals("not ready")) {
                    readyFromHome.getStyleClass().add("notreadyred");
                }

                Label readyFromCampus = new Label(schedule_of_student.getEnd_state());
                if ((readyFromCampus.getText()).equals("ready")) {
                    readyFromCampus.getStyleClass().add("readygreen");
                } else if ((readyFromCampus.getText()).equals("not ready")) {
                    readyFromCampus.getStyleClass().add("notreadyred");
                }

                info.add(new Label("Neighborhood: "), 0, 0);
                info.add(new Label("Phone No.: "), 0, 1);
                info.add(new Label("Pick up from home:"), 0, 2);
                info.add(new Label("Pick up from campus:"), 0, 3);
                info.add(new Label(studentAddress.getNeighborhood()), 1, 0);
                info.add(new Label(student.getPhoneNumber()), 1, 1);
                info.add(readyFromHome, 1, 2);
                info.add(readyFromCampus, 1, 3);

                VBox studentVbox = new VBox();
                studentVbox.getStyleClass().add("sec");
                studentVbox.setPadding(new Insets(10, 10, 10, 10));
                studentVbox.getChildren().add(name);
                studentVbox.getChildren().add(info);
                studentVbox.setMaxWidth(300);
                studentVbox.setPrefWidth(280);
                VBox.setMargin(name, new Insets(0, 0, 2, 5));
                VBox.setMargin(info, new Insets(0, 0, 2, 5));

                container.getChildren().add(studentVbox);
                //container.setMargin();

            }
        } else {
            ImageView no_students = new ImageView(new Image("nothing-here.png"));
            no_students.setPreserveRatio(true);
            no_students.setFitWidth(300);

            Label no_studentsLabel = new Label("No Registred students");
            no_studentsLabel.getStyleClass().add("gridpane-label");
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(no_students, no_studentsLabel);
        }
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

}
