/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class Reset_passwordController implements Initializable {

    @FXML
    private PasswordField new_password;
    @FXML
    private PasswordField confirm_new_password;

    private int[] invokedByChain = new int[2];

    private String backToScreen, goToScreen, table;

    //private int id;
    //private String table
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        switch (LoginController.userCode) {
            case 1:
                System.out.println(LoginController.userCode + "@#$%^&*(#$%^&*");
                backToScreen = "student_myProfile.fxml";
                goToScreen = "enterYourPasswordToEdit.fxml";
                table = "Student";
                break;
            case 2:
                System.out.println(LoginController.userCode + "@#$%^&*(#$%^&*");
                backToScreen = "driver_myProfile.fxml";
                goToScreen = "enterYourPasswordToEdit.fxml";
                table = "Driver";

                break;
            default:
                System.out.println(LoginController.userCode + "@#$%^&*(#$%^&*");
                backToScreen = goToScreen = "login.fxml";
                if (ForgotPasswordController.resetUserCode == 1) {
                    table = "Student";
                } else {
                    table = "Driver";
                }
                break;
        }
    }

    public void setInvokedByChain(int[] invokedByChain) {
        this.invokedByChain = invokedByChain;
    }

    @FXML
    private void rest_and_redirct(ActionEvent event) throws IOException {

        //TODO => edit database
        if (!new_password.getText().isEmpty() && new_password.getText().equals(confirm_new_password.getText())) {

            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(" FROM " + table + " WHERE email =" + "\'" + ForgotPasswordController.resetEmail + "\'");
            if (table.equals("Student")) {
                Student student = (Student) query.uniqueResult();
                student.setPassword(new_password.getText());
                session.update(student);
            } else {
                Driver driver = (Driver) query.uniqueResult();
                driver.setPassword(new_password.getText());
                session.update(driver);
            }

            tx.commit();
            session.close();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Done!");
            confirm.setHeaderText(null);
            confirm.setContentText("You will be redirected to enter your new password");
            confirm.show();
            Parent nextPage = FXMLLoader.load(getClass().getResource(goToScreen));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Something went wrong");
            error.setHeaderText(null);
            error.setContentText("Please enetr matching passwords");
            error.show();
        }

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource(backToScreen));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

}
