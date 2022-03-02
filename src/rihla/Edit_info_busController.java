/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class Edit_info_busController implements Initializable {

    @FXML
    private ComboBox<Integer> capacity;
    @FXML
    private ListView trips_times_from_campus_listview;
    @FXML
    private ListView trips_times_to_campus_listview;
    @FXML
    private TextField license_number;
    @FXML
    private TextField plate_number;
    @FXML
    private TextField bus_owner;
    @FXML
    private ComboBox<String> gateNumber;

    Session session;

    Transaction transaction;

    public static Bus bus;

    public static boolean changeBus = false, changeTripsToCampus = false, changeTripsFromCampus = false;

    protected static ObservableList<String> tripsTimes_fromCampus = FXCollections.observableArrayList();

    protected static ObservableList<String> tripsTimes_toCampus = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Query query = session.createQuery("From Bus where busId =" + LoginController.driver.getBusId());
        bus = (Bus) query.uniqueResult();

        transaction.commit();
        session.close();

        bus_owner.setText(bus.getOwner());

        license_number.setText(Integer.toString(bus.getLicenseNumber()));

        plate_number.setText(bus.getPlateNumber());

        trips_times_from_campus_listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        trips_times_to_campus_listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList capacities = FXCollections.observableArrayList(6, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
        capacity.setItems(capacities);
        capacity.setValue(bus.getCapacity());

        ObservableList trips_times_from_campus_List = FXCollections.observableArrayList("10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "3:00PM", "04:00PM", "05:00PM", "06:00PM");
        trips_times_from_campus_listview.setItems(trips_times_from_campus_List);

        trips_times_from_campus_listview.getSelectionModel().selectedItemProperty().addListener(e -> {
            tripsTimes_fromCampus = trips_times_from_campus_listview.getSelectionModel().getSelectedItems();
        });

        ObservableList trips_times_to_campus_List = FXCollections.observableArrayList("07:00AM", "08:00AM", "09:00AM", "10:00AM", "11:00AM", "12:00PM", "01:00PM", "02:00PM", "3:00PM");
        trips_times_to_campus_listview.setItems(trips_times_to_campus_List);

        trips_times_to_campus_listview.getSelectionModel().selectedItemProperty().addListener(e -> {
            tripsTimes_toCampus = trips_times_to_campus_listview.getSelectionModel().getSelectedItems();
        });

        ObservableList gatesnumber = FXCollections.observableArrayList("Al juhara", "2", "3", "4");
        gateNumber.setItems(gatesnumber);
        gateNumber.setValue(bus.getGateNumber());

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        if (bus_owner.getText().isEmpty()
                || license_number.getText().isEmpty()
                || plate_number.getText().isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill all the fields");
            error.show();

        } else {
            bus.setBusId(LoginController.driver.getBusId());
            bus.setCapacity(capacity.getValue());
            bus.setOwner(bus_owner.getText());
            bus.setPlateNumber(plate_number.getText());
            bus.setLicenseNumber(Integer.parseInt(license_number.getText()));
            bus.setGateNumber(gateNumber.getValue());

            changeBus = true;

            if (!tripsTimes_fromCampus.isEmpty()) {
                changeTripsFromCampus = true;
            }
            if (!tripsTimes_toCampus.isEmpty()) {
                changeTripsToCampus = true;
            }
            Parent nextPage = FXMLLoader.load(getClass().getResource("edit_info_driver.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }
    }

}
