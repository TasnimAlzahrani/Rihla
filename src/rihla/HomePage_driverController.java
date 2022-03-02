/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class HomePage_driverController implements Initializable {

    @FXML
    private ImageView home;
    @FXML
    private ImageView profile;
    @FXML
    private ImageView notification;
    @FXML
    private ImageView studentList;
    @FXML
    private ImageView missingItem;

    @FXML
    private Text name;

    @FXML
    private Label date;

    @FXML
    private ListView tocampus;
    @FXML
    private ListView fromcampus;

    Driver d; //= LoginController.driver;
    int id = 1;//d.getDriverId();
    int busnumber = 1;//d.getBusId();

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Driver> dlist = null;
        String queryStr3 = "from Driver d where d.driverId = " + LoginController.driver.getDriverId()/*sDriver*/;
        Query query3 = session.createQuery(queryStr3);
        d = (Driver) query3.uniqueResult();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        List<TripsSchedule_toCampus> toList = null;

        String queryStr = "from TripsSchedule_toCampus s where s.busId = " + d.getBusId();
        Query query = session.createQuery(queryStr);
        toList = query.list();
        session.close();
        System.out.println("list size: " + toList.size());
        for (TripsSchedule_toCampus t : toList) {
            System.out.println(t.getBusId() + " - " + t.getToCampusTime());
        }

        List<String> toList2 = new ArrayList<String>();
        System.out.println(toList.size());
        for (TripsSchedule_toCampus t : toList) {
            if (t.getToCampusTime().length() == 4) {
                toList2.add("0" + t.getToCampusTime());
            } else {
                toList2.add(t.getToCampusTime());
            }
        }

        session = HibernateUtil.getSessionFactory().openSession();
        List<TripsSchedule_fromCampus> fromList = null;
        String queryStr2 = "from TripsSchedule_fromCampus s where s.busId = " + d.getBusId()/**/;
        Query query2 = session.createQuery(queryStr2);
        fromList = query2.list();
        session.close();

        List<String> fromList2 = new ArrayList<String>();
        List<String> fromList3 = new ArrayList<String>();
        for (TripsSchedule_fromCampus t : fromList) {

            if (t.getFromCampusTime().length() == 4) {
                fromList2.add("0" + t.getFromCampusTime());
            } else {
                fromList3.add(t.getFromCampusTime());
            }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");

        LocalDateTime now = LocalDateTime.now();
        Month m = now.getMonth();
        DayOfWeek day = DayOfWeek.from(now);
        String day2 = day.name().toLowerCase();
        System.out.println(day.name().substring(0, 1) + day2 + ", " + dtf.format(now));

        int m2 = m.getValue();
        String m3 = "";
        switch (m2) {
            case 1:
                m3 = "Jan";
                break;
            case 2:
                m3 = "Feb";
                break;
            case 3:
                m3 = "Mar";
                break;
            case 4:
                m3 = "Apr";
                break;
            case 5:
                m3 = "May";
                break;
            case 6:
                m3 = "Jun";
                break;
            case 7:
                m3 = "Jul";
                break;
            case 8:
                m3 = "Aug";
                break;
            case 9:
                m3 = "Sep";
                break;
            case 10:
                m3 = "Oct";
                break;
            case 11:
                m3 = "Nov";
                break;
            case 12:
                m3 = "Dec";
                break;

        }

        int space = d.getName().indexOf(" ");
        name.setText(d.getName().substring(0, space));
        date.setText(day.name().substring(0, 1) + day2.substring(1) + ", " + dtf.format(now) + " " + m3);

        List<String> sortedList = toList2.stream().sorted().collect(Collectors.toList());
        //List<String> sortedList2 = fromList2.stream().sorted().collect(Collectors.toList());     

        tocampus.getItems().addAll(sortedList);
        fromcampus.getItems().addAll(fromList3);
        fromcampus.getItems().addAll(fromList2);
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
    private void missingItems(MouseEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_driver.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }
}
