/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Eman Alharbi
 */
public class LoginController implements Initializable {

    @FXML
    private Label label;

    @FXML
    TextField email;

    @FXML
    TextField password;

    @FXML
    Button leftbtn;

    @FXML
    Button rightbtn;

    @FXML
    private Button loginbtn;

    public static Object user;

    public static int userCode = 0;

    public static Student student;

    public static Driver driver;

    public static String emailAddress;

    @FXML
    public void labelevent(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("forgotPassword.fxml"));
        Parent nextPage = loader.load();
        ForgotPasswordController controller = loader.getController();

        controller.setInvokedBy(1);

        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    public void leftbtnEvent(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("rihlaStartScene.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(forgetpasswordscene);
        window.show();

    }

    @FXML
    public void loginevent(ActionEvent event) throws IOException {

        if (missingFieldAlert() == true) {

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery(" FROM Student WHERE email =" + "\'" + email.getText() + "\' and password =" + "\'" + password.getText() + "\'");

            if (query.list().isEmpty()) {

                query = session.createQuery(" FROM Driver WHERE email =" + "\'" + email.getText() + "\' and password =" + "\'" + password.getText() + "\'");
                if (query.list().isEmpty()) {

                } else {
                    driver = (Driver) query.uniqueResult();
                    userCode = 2;
                    emailAddress = email.getText();
                }

            } else {
                student = (Student) query.uniqueResult();
                userCode = 1;
                emailAddress = email.getText();
            }

            tx.commit();
            session.close();

            if (userCode != 0) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("verification.fxml"));
                Parent nextPage = loader.load();
                VerificationController controller = loader.getController();

                int[] invokedByChain = {0, 1};

                controller.setInvokedByChain(invokedByChain);

                Scene next = new Scene(nextPage);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(next);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Try again");
                alert.setContentText("Incorrect email or password");
                alert.showAndWait();
            }
        }
    }

    public boolean missingFieldAlert() {

        StringBuilder errors = new StringBuilder();

        // Confirm mandatory fields are filled out
        if (email.getText().trim().isEmpty()) {
            errors.append("- Please enter a email.\n");
        }
        if (password.getText().trim().isEmpty()) {
            errors.append("- Please enter a password.\n");

        }

        // If any missing information is found, show the error messages and return false
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        // No errors
        return true;

    }

    public boolean missinginfoAlert() {

        StringBuilder errors = new StringBuilder();
        if (email.getText().equals(student.getEmail())) {
            errors.append("-Wrong Email\n");
        }

        if (password.getText().equals(student.getPassword())) {
            errors.append("-Wrong password\n");
        }

        if (!email.getText().equals(driver.getEmail())) {
            errors.append("-Wrong Email\n");
        }

        if (!password.getText().equals(driver.getPassword())) {
            errors.append("-Wrong password\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }

        // No errors
        return true;

    }

    /*if(email.getText()!=student.getEmail()){
 errors.append("-Wrong Email\n");
}

if(password.getText()!=student.getPassword()){
     errors.append("-Wrong password\n");
}

if(email.getText()!=driver.getEmail()){
 errors.append("-Wrong Email\n");
}

if(password.getText()!=driver.getPassword()){
     errors.append("-Wrong password\n");
}
     */
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
