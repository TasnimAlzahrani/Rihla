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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static rihla.LoginController.driver;
import static rihla.LoginController.student;
import static rihla.LoginController.userCode;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class ForgotPasswordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int invokedBy;

    public static String resetEmail;

    public static int resetUserCode = 0;

    @FXML
    private TextField email;

    //private int id;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setInvokedBy(int invokedBy) {
        this.invokedBy = invokedBy;
    }

    private boolean chackEmail() {

        boolean flag;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(" FROM Student WHERE email =" + "\'" + email.getText() + "\'");
        if (query.list().isEmpty()) {
            query = session.createQuery(" FROM Driver WHERE email =" + "\'" + email.getText() + "\'");
            if (query.list().isEmpty()) {
                flag = false;
            } else {
                Driver driver = (Driver) query.uniqueResult();
                resetEmail = driver.getEmail();
                resetUserCode = 2;
                flag = true;
            }
        } else {
            Student student = (Student) query.uniqueResult();
            resetEmail = student.getEmail();
            resetUserCode = 1;
            flag = true;
        }

        tx.commit();
        session.close();

        return flag;
    }

    @FXML
    //go back to enter your password
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        if (invokedBy == 1) {
            Parent page = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(page);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Parent page = FXMLLoader.load(getClass().getResource("enterYourPasswordToEdit.fxml"));
            Scene scene = new Scene(page);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    //when clicking send
    private void verify(ActionEvent event) throws IOException {

        if (!email.getText().isEmpty() && chackEmail()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("verification.fxml"));
            Parent nextPage = loader.load();
            VerificationController controller = loader.getController();

            int[] invokedByChain = {invokedBy, 2};

            controller.setInvokedByChain(invokedByChain);

            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        } else {
            Alert e = new Alert(Alert.AlertType.ERROR);
            e.setTitle("Something went wrong");
            e.setHeaderText(null);
            e.setContentText("Please enter correct email");
            e.show();
        }

    }

}
