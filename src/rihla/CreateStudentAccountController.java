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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static rihla.CreateDriverAccountController.neighborhoodsList;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class CreateStudentAccountController implements Initializable {

    @FXML
    private ComboBox<String> cities;
    @FXML
    private ListView<String> neighborhoods;

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

    private ObservableList<String> neighborhoodsList;

    public static Student student;

    public static StudentAddress studentAddress;
    @FXML
    private TextField street;

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
        if (student != null) {
            fill();
        } else {
            cities.setValue("Choose a city");
        }
        ObservableList<String> citiesList = FXCollections.observableArrayList("Makkah", "Jaddah");

        cities.setItems(citiesList);

        fee_slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                fee.setText("fee: " + Integer.toString((int) fee_slider.getValue()) + "SR");
            }
        });

    }

    private void fill() {
        first_name.setText(student.getName().substring(0, student.getName().indexOf(' ')));

        last_name.setText(student.getName().substring(student.getName().indexOf(' ') + 1));

        email.setText(student.getEmail());

        fee_slider.setValue(student.getConvenientPrice());

        cities.setValue(studentAddress.getCity());

        phone_number.setText(student.getPhoneNumber());

        street.setText(studentAddress.getStreet());

    }

    @FXML

    private void display_neighborhoods(ActionEvent event) {
        setNeighborhoods((String) cities.getValue());
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("chooseAccountType.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void goForward(ActionEvent event) throws IOException {

        String domain = "";
        if (email.getText().contains("@")) {
            int index = email.getText().indexOf("@");
            domain = email.getText().substring(index);
        }

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

        } else if (phone_number.getText().length() != 10) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("WARNING");
            error.setHeaderText(null);
            error.setContentText("The length of Mobile number must be 10 digits");
            error.show();

        } else if (!(domain.equals("@gmail.com")) && !(domain.equals("@st.uqu.edu.sa")) && !(domain.equals("@yahoo.com")) && !(domain.equals("@outlook.com")) && !(domain.equals("@hotmail.com")) && !(domain.equals("@live.com"))) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("WARNING");
            error.setHeaderText(null);
            error.setContentText("Please enter a valid e-mail");
            error.show();

        } else {
            studentAddress = new StudentAddress();

            studentAddress.setCity(cities.getValue());
            studentAddress.setNeighborhood(neighborhoods.getSelectionModel().getSelectedItem());
            studentAddress.setStreet(street.getText());

            student = new Student();

            student.setName(first_name.getText() + " " + last_name.getText());
            student.setEmail(email.getText());
            student.setPassword(password.getText());
            student.setPhoneNumber(phone_number.getText());
            student.setConvenientPrice(fee_slider.getValue());

            Parent nextPage = FXMLLoader.load(getClass().getResource("studentSchedule.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }
    }

}
