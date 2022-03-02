/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
public class Driver_myProfileController implements Initializable {

    @FXML
    private Circle driver_photo;

    private int id;
    @FXML
    private Label ID;
    @FXML
    private Label phonenumber;
    @FXML
    private Label age;
    @FXML
    private Label nationality;
    @FXML
    private Label licensenumber;
    @FXML
    private Label platenumber;
    @FXML
    private Label busowner;
    @FXML
    private Label email;
    @FXML
    private Text name;

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set the labels
        String calculated_age = Integer.toString(Period.between(LocalDate.parse(LoginController.driver.getDateOfbirth()), LocalDate.now()).getYears());
        age.setText(calculated_age);
        phonenumber.setText(LoginController.driver.getPhone_number());
        ID.setText(Integer.toString(LoginController.driver.getDriverId()));
        nationality.setText(LoginController.driver.getNationality());
        email.setText(LoginController.driver.getEmail());
        name.setText((String) LoginController.driver.getName());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("From Bus where busId= " + LoginController.driver.getBusId());
        Bus bus = (Bus) query.uniqueResult();
        tx.commit();
        session.close();

        licensenumber.setText(Integer.toString(bus.getLicenseNumber()));
        busowner.setText(bus.getOwner());
        platenumber.setText(bus.getPlateNumber());

        FileOutputStream fos;
        try {
            fos = new FileOutputStream("output.jpg");
            fos.write(LoginController.driver.getPhoto());
            fos.close();
        } catch (IOException e) {

        }

        ImagePattern driverImagePattern = new ImagePattern(new Image("file:output.jpg"));
        driver_photo.setFill(driverImagePattern);

    }

    ///////////// navigation bar /////////////////
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
    private void enterPassword(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("enterYourPasswordToEdit.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }
}
