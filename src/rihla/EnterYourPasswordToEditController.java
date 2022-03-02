/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Tasneem Al-Zahrani
 */
public class EnterYourPasswordToEditController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String backToScreen, editScreen, table, tableId;
    private int userId;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        switch (LoginController.userCode) {
            case 1:
                backToScreen = "student_myProfile.fxml";
                editScreen = "edit_info_student.fxml";
                table = "Student";
                tableId = "studentId";
                userId = LoginController.student.getStudentId();
                break;
            case 2:

                backToScreen = "driver_myProfile.fxml";
                editScreen = "edit_info_driver.fxml";
                table = "Driver";
                tableId = "driverId";
                userId = LoginController.driver.getDriverId();
                break;
            default:
                backToScreen = "login.fxml";
                break;
        }
    }

    private boolean checkPassword() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("FROM " + table + " WHERE " + tableId + " = " + "\'" + userId + "\' and password =" + "\'" + password.getText() + "\'");
        Object user = query.uniqueResult();
        tx.commit();
        session.close();

        return user != null;

    }

    @FXML
    //when click on back button
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource(backToScreen));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void edit_info(ActionEvent event) throws IOException {

        if (checkPassword()) {
            Parent nextPage = FXMLLoader.load(getClass().getResource(editScreen));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please enter correct password");
            error.show();
        }
    }

    @FXML
    private void forgotPassword(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("forgotPassword.fxml"));
        Parent nextPage = loader.load();
        ForgotPasswordController controller = loader.getController();
        controller.setInvokedBy(2);
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }
}
