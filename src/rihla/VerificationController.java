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
import javafx.stage.Stage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javax.mail.Session; //handles configuration setting and authentication
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message; //creating a message that will be sent
import javax.mail.Transport; //a message transport mechanism (will use the SMTP protocol to send the messages)
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class VerificationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private int[] invokedByChain = new int[2];
    @FXML
    private TextField codeField;

    //private int id;
    //private String email
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            secondFactor(LoginController.emailAddress);
        } catch (Exception ex) {
            Logger.getLogger(VerificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setInvokedByChain(int[] invokedByChain) {
        this.invokedByChain = invokedByChain;
    }

    @FXML
    //when click back button
    private void goBack(ActionEvent event) throws IOException {

        String goBack;
        if (invokedByChain[1] == 1) {
            Parent page = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(page);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("forgotPassword.fxml"));
            Parent nextPage = loader.load();
            ForgotPasswordController controller = loader.getController();

            controller.setInvokedBy(invokedByChain[0]);

            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }

    }

    public int Authentication() {
        int max = 9999;
        int min = 1;
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

    //content of the email and then send it 
    private Message sendVerficationEmail(Session session, String email, String userEmail, int random) {
        try {
            //creating the message + passing session
            Message message = new MimeMessage(session);
            //set the email of sender
            message.setFrom(new InternetAddress(email));
            //set email of the recipient
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            //content of the email
            message.setSubject("Verfication Email");
            message.setText("your verfication code: " + random);
            session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
            //return the email
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(Reset_passwordController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    int random = 0;

    public void secondFactor(String userEmail) throws Exception {
        //used to configuring properties 
        Properties properties = new Properties();
        //attempt to authenticate the user using the AUTH command
        properties.put("mail.smtp.auth", "true");
        //for TLS encryption => switch the connection to a TLS-protected connection before issuing any login commands
        properties.put("mail.smtp.starttls.enable", "true");
        //emails' server to connect to => gmail
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //emails' server port to connect to => 587
        properties.put("mail.smtp.port", "587");
        //the emailinformation that send the number
        String From = "RihlaProject2021@gmail.com";
        String pass = "Rihla123321";

        //web session
        //needed to sign in and send emails 
        Session session;
        session = Session.getInstance(
                properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //a repository for a user name and a password.
                return new PasswordAuthentication(From, pass);
            }
        }
        );

        //get the random number from the method
        random = Authentication();

        //////////////////////////////
        ////the verfication email////
        ////////////////////////////
        //creating the message and provide the content as a parameters 
        Message message = sendVerficationEmail(session, From, userEmail, random);
        //send it via SMTP protocol
        Transport.send(message);

        System.out.println("sent");

    }

    @FXML
    private void nextPage(ActionEvent event) throws IOException, Exception {

        if (codeField.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill all the fields");
            error.show();
        }
        if (Integer.parseInt(codeField.getText()) == random) {
            if (invokedByChain[1] == 1) {
                System.out.println("yes");
                if (LoginController.userCode == 1) {

                    Parent page = FXMLLoader.load(getClass().getResource("homePage_student.fxml"));
                    Scene scene = new Scene(page);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Parent page = FXMLLoader.load(getClass().getResource("homePage_driver.fxml"));
                    Scene scene = new Scene(page);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                Parent nextPage = FXMLLoader.load(getClass().getResource("reset_password.fxml"));
                Scene next = new Scene(nextPage);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(next);
                stage.show();
            }

        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Enetr correct code");
            error.show();
        }
    }
}
