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
public class Student_myProfileController implements Initializable {

    @FXML
    private Label studentId;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label address;
    @FXML
    private Label expectedFee;
    @FXML
    private Label email;
    @FXML
    private Text name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentId.setText(Integer.toString(LoginController.student.getStudentId()));

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM StudentAddress WHERE zipcode =" + LoginController.student.getZipcode());
        StudentAddress studentAddress = (StudentAddress) query.uniqueResult();
        tx.commit();
        session.close();

        name.setText(LoginController.student.getName());

        phoneNumber.setText(LoginController.student.getPhoneNumber());

        address.setText(studentAddress.toString());

        expectedFee.setText(Double.toString(LoginController.student.getConvenientPrice()));

        email.setText(LoginController.student.getEmail());

    }

    private void exit(MouseEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("rihlaStartScene.fxml"));
        Scene naxtPage = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(naxtPage);
        stage.show();

    }

    @FXML
    //when click on edit personal info

    private void enterPassword(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("enterYourPasswordToEdit.fxml"));
        Scene naxtPage = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(naxtPage);
        stage.show();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("enterYourPasswordToEdit.fxml"));
//
////        Parent nextPage = FXMLLoader.load(getClass().getResource("enterYourPasswordToEdit.fxml"));
//        Parent nextPage = loader.load();
//        EnterYourPasswordToEditController controller = loader.getController();
//        Scene next = new Scene(nextPage);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(next);
//        stage.show();
    }

    @FXML
    private void homepage(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("homePage_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void myprofile(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("student_myProfile.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void notifications(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("notification_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void assignedBus(ActionEvent event) throws IOException {
        if (LoginController.student.getDriverId() != null) {
            Parent forgetpassword = FXMLLoader.load(getClass().getResource("assignedBus.fxml"));
            Scene forgetpasswordscene = new Scene(forgetpassword);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(forgetpasswordscene);
            stage.show();
        } else {
            Parent forgetpassword = FXMLLoader.load(getClass().getResource("noAssignedBus.fxml"));
            Scene forgetpasswordscene = new Scene(forgetpassword);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(forgetpasswordscene);
            stage.show();
        }
    }

    @FXML
    private void missingItems(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
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
