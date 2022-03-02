/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static rihla.CreateDriverAccountController.cardInformation;
import static rihla.CreateDriverAccountController.cityCode;
import static rihla.CreateDriverAccountController.servedNeighborhood;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class CreateDriverAccount_busInformationController implements Initializable {

    @FXML
    private ComboBox<Integer> capacity;
    @FXML
    private ListView trips_times_from_campus_listview;
    @FXML
    private ListView trips_times_to_campus_listview;

    private ObservableList<String> trips_times_from_campus_list;

    private ObservableList<String> trips_times_to_campus_list;

    private TripsSchedule_fromCampus tripsSchedule_fromCampus;

    private TripsSchedule_toCampus tripsSchedule_toCampus;

    private ServedNeighborhood servedNeighborhood;

    private Bus bus = new Bus();
    @FXML
    private TextField license_number;
    @FXML
    private TextField plate_number;
    @FXML
    private TextField bus_owner;
    @FXML
    private ComboBox<String> gateNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set contollers values
        trips_times_from_campus_listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        trips_times_to_campus_listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList capacities = FXCollections.observableArrayList(6, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
        capacity.setItems(capacities);

        ObservableList trips_times_from_campus_List = FXCollections.observableArrayList("10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "3:00PM", "04:00PM", "05:00PM", "06:00PM");
        trips_times_from_campus_listview.setItems(trips_times_from_campus_List);

        ObservableList trips_times_to_campus_List = FXCollections.observableArrayList("07:00AM", "08:00AM", "09:00AM", "10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "03:00PM");
        trips_times_to_campus_listview.setItems(trips_times_to_campus_List);

        ObservableList gatesnumber = FXCollections.observableArrayList("Al juhara", "2", "3", "4");
        gateNumber.setItems(gatesnumber);

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("createDriverAccount.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void goForward(ActionEvent event) {

        try {

            //store info in object
            bus.setCapacity(capacity.getValue());
            bus.setGateNumber(gateNumber.getValue());
            bus.setLicenseNumber(Integer.parseInt(license_number.getText()));
            bus.setPlateNumber(plate_number.getText());
            bus.setOwner(bus_owner.getText());

            //assign the bus to this driver
            CreateDriverAccountController.driver.setBusId(bus.getBusId());

            //store ibjects in database
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(bus);
            CreateDriverAccountController.driver.setBusId(bus.getBusId());
            session.save(CreateDriverAccountController.driver);
            tx.commit();
            session.close();

            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            //for each neighborhood choosen by the user 
            for (String string : CreateDriverAccountController.servedNeighborhoods) {
                servedNeighborhood = new ServedNeighborhood();
                servedNeighborhood.setDriverId(CreateDriverAccountController.driver.getDriverId());
                System.out.println(cityCode + CreateDriverAccountController.neighborhoodsList.indexOf(string));
                servedNeighborhood.setNeighborhoodId(cityCode + CreateDriverAccountController.neighborhoodsList.indexOf(string));
                session.save(servedNeighborhood);
            }

            //for each time from campus choosen by the user 
            for (String string : trips_times_from_campus_list) {
                tripsSchedule_fromCampus = new TripsSchedule_fromCampus();
                tripsSchedule_fromCampus.setBusId(bus.getBusId());
                tripsSchedule_fromCampus.setFromCampusTime(string);
                session.save(tripsSchedule_fromCampus);
            }

            //for each time to campus choosen by the user
            for (String string : trips_times_to_campus_list) {
                tripsSchedule_toCampus = new TripsSchedule_toCampus();
                tripsSchedule_toCampus.setBusId(bus.getBusId());
                tripsSchedule_toCampus.setToCampusTime(string);
                session.save(tripsSchedule_toCampus);
            }
            //cardInformation list imported above
            //for each card in the list
            for (CardInformation card : cardInformation) {
                if (card != null) {
                    card.setDriverId(CreateDriverAccountController.driver.getDriverId());
                    session.save(card);
                }
            }
            tx.commit();
            session.close();

            //show alert the redirect to login
            Alert redirect = new Alert(Alert.AlertType.CONFIRMATION);
            redirect.setTitle("");
            redirect.setHeaderText(null);
            redirect.setContentText("Done! you can now log in to use the system");
            redirect.showAndWait();
            Parent nextPage = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("???");
            error.show();
        }

    }

    @FXML
    private void getSelectedItems(MouseEvent event) {
        trips_times_from_campus_list = trips_times_from_campus_listview.getSelectionModel().getSelectedItems();

    }

    @FXML
    private void getSelectedItems2(MouseEvent event) {
        trips_times_to_campus_list = trips_times_to_campus_listview.getSelectionModel().getSelectedItems();
    }

}
