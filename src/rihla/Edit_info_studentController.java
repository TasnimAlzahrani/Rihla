/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class Edit_info_studentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<String> cities;
    private Slider price;
    private Label selected_price;
    @FXML
    private ListView<String> neighborhoods;

    private ObservableList<String> neighborhoodsList;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField phone_number;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField confirm_password;
    @FXML
    private Slider fee_slider;
    @FXML
    private Label fee;
    @FXML
    private TextField street;

    private StudentAddress studentAddress;

    private void setNeighborhoods(String city) {

        if (city.equals("Makkah")) {
            neighborhoodsList = FXCollections.observableArrayList("AlAwali", "AlSharaya", "AlHamra", "AlNuzha", "AlZahir", "AlZaidee", "AlEskan", "Azizyah", "AlShawqiyyah", "AlShumaisi", "AlHejra", "AlKhadra");
        } else {
            neighborhoodsList = FXCollections.observableArrayList("AlNaseem", "AlRabua", "AlNuzha", "AlHamra", "AlMurjan", "AlKhlidia", "AlRehab", "AlManar", "AlAndalus", "AlRabwa", "AlRawabi", "Albasateen");
        }
        neighborhoods.setItems(neighborhoodsList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fee_slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                fee.setText("fee: " + Integer.toString((int) fee_slider.getValue()) + "SR");
            }
        });

        first_name.setText(LoginController.student.getName().substring(0, LoginController.student.getName().indexOf(' ')));

        last_name.setText(LoginController.student.getName().substring(LoginController.student.getName().indexOf(' ') + 1));

        phone_number.setText(LoginController.student.getPhoneNumber());

        email.setText(LoginController.student.getEmail());

        fee_slider.setValue(LoginController.student.getConvenientPrice());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From StudentAddress where zipcode=" + LoginController.student.getZipcode());
        studentAddress = (StudentAddress) query.uniqueResult();
        tx.commit();
        session.close();

        password.setText(LoginController.student.getPassword());

        ObservableList<String> citiesList = FXCollections.observableArrayList("Makkah", "Jaddah");
        cities.setValue(studentAddress.getCity());
        cities.setItems(citiesList);

        setNeighborhoods(studentAddress.getCity());

        int neighborhood = neighborhoodsList.indexOf(studentAddress.getNeighborhood());

        neighborhoods.getSelectionModel().select(neighborhood);
        neighborhoods.getFocusModel().focus(neighborhood);
        neighborhoods.scrollTo(neighborhood);

        street.setText(studentAddress.getStreet());

    }

    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("student_myProfile.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void myProfile(ActionEvent event) throws IOException {
        if (cities.getValue().equals("Choose a city")
                || neighborhoods.getSelectionModel().getSelectedItems().isEmpty()
                || first_name.getText().isEmpty()
                || last_name.getText().isEmpty()
                || phone_number.getText().isEmpty()
                || email.getText().isEmpty()
                || password.getText().isEmpty()
                || confirm_password.getText().isEmpty()
                || fee.getText().isEmpty()
                || street.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill all the fields");
            error.show();

        } else if (!password.getText().equals(confirm_password.getText())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please enter matching passwords");
            error.show();

        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            studentAddress = (StudentAddress) session.get(StudentAddress.class, LoginController.student.getZipcode());
            studentAddress.setStreet(street.getText());
            studentAddress.setCity(cities.getValue());
            studentAddress.setNeighborhood(neighborhoods.getSelectionModel().getSelectedItem());
            session.update(studentAddress);

            LoginController.student.setName(first_name.getText() + " " + last_name.getText());
            LoginController.student.setEmail(email.getText());
            LoginController.student.setPassword(password.getText());
            LoginController.student.setPhoneNumber(phone_number.getText());
            LoginController.student.setConvenientPrice(fee_slider.getValue());
            session.update(LoginController.student);

            tx.commit();
            session.close();
            Parent nextPage = FXMLLoader.load(getClass().getResource("student_myProfile.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }
    }

    @FXML
    private void display_neighborhoods(ActionEvent event) {
        setNeighborhoods((String) cities.getValue());
    }
}
