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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class AssignedBus_driverInformationController implements Initializable {

    @FXML
    private Circle driver_photo;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text name;
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
    private VBox driver_vbox;
    @FXML
    private VBox cards_vbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //1) get the driver information from database
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From Driver where driverId= " + LoginController.student.getDriverId());
        Driver driver = (Driver) query.uniqueResult();

        transaction.commit();
        session.close();

        //set the labels values
        //calculate age
        String calculated_age = Integer.toString(Period.between(LocalDate.parse(driver.getDateOfbirth()), LocalDate.now()).getYears());

        age.setText(calculated_age);
        phonenumber.setText(driver.getPhone_number());
        ID.setText(Integer.toString(driver.getDriverId()));
        nationality.setText(driver.getNationality());
        name.setText((String) driver.getName());

        //2)get the bus information from database
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        query = session.createQuery("From Bus where busId= " + driver.getBusId());
        Bus bus = (Bus) query.uniqueResult();
        transaction.commit();
        session.close();

        //set the labels values
        licensenumber.setText(Integer.toString(bus.getLicenseNumber()));
        busowner.setText(bus.getOwner());
        platenumber.setText(bus.getPlateNumber());

        //use a file outputstream to generate a file of the driver's photo to daisplay it on the screen
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("output.jpg");
            fos.write(driver.getPhoto()); //read from byte array
            fos.close();
        } catch (IOException e) {

        }

        //display it on the screen
        ImagePattern driverImagePattern = new ImagePattern(new Image("file:output.jpg"));
        driver_photo.setFill(driverImagePattern);

        //get driver's card information
        showCards();

    }

    //go back button navigation
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("assignedBus.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //the student can cancel the BUS 
    @FXML
    private void cancel_bus(ActionEvent event) {
        //show a warining with cancel and yes button
        Alert e = new Alert(Alert.AlertType.WARNING);
        e.setTitle("");
        e.setHeaderText(null);
        e.setContentText("This will remove you from this bus student list, are you sure?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        e.getButtonTypes().setAll(yesButton, cancelButton);
        Optional<ButtonType> result = e.showAndWait();

        //if the user pressed yes
        if (result.get() == yesButton) {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Student s2 = null;
            //get the student id
            int sid = LoginController.student.getStudentId();
            //read the student with the id = sid information 
            s2 = (Student) session.get(Student.class, sid);
            //set driver id column to be null
            s2.setDriverId(null);
            session.getTransaction().commit();
            session.close();
            LoginController.student.setDriverId(null);

        }
    }

    //go to change bus information when click on the button 
    @FXML
    private void change_bus(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("changeBus.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // display driver's cards ( if there is any)
    private void showCards() {
        //get the list of cards
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("From CardInformation where driverId=" + LoginController.student.getDriverId());
        List<CardInformation> cardInformation = query.list();

        transaction.commit();
        session.close();

        //if the list is not empty (empty != null)
        if (!cardInformation.isEmpty()) {
            //for each card
            for (CardInformation cardinfo : cardInformation) {

                //create a box 
                VBox card = new VBox(15);
                card.setPrefSize(320, 146);
                card.setAlignment(Pos.CENTER);
                card.setPadding(new Insets(0, 20, 0, 20));
                card.getStyleClass().add("card");

                //set the box label
                Label cardLabel = new Label("Card information");
                cardLabel.setTextAlignment(TextAlignment.CENTER);
                cardLabel.setPrefHeight(17);
                cardLabel.setPrefWidth(333);
                cardLabel.getStyleClass().add("gridpane-label2");
                cardLabel.setAlignment(Pos.CENTER);

                //create a grid
                GridPane card_form = new GridPane();
                card_form.setVgap(5);
                card_form.setPrefSize(296, 52);

                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPrefWidth(110);
                column1.setMinWidth(10);
                column1.setMaxWidth(138);
                column1.setHgrow(Priority.SOMETIMES);
                card_form.getColumnConstraints().add(column1);

                ColumnConstraints column2 = new ColumnConstraints();
                column2.setPrefWidth(170);
                column2.setMinWidth(10);
                column2.setMaxWidth(231.33331298828125);
                column2.setHgrow(Priority.SOMETIMES);
                card_form.getColumnConstraints().add(column2);

                RowConstraints row1 = new RowConstraints();
                row1.setPrefHeight(30);
                row1.setMinHeight(10);
                row1.setVgrow(Priority.SOMETIMES);
                card_form.getRowConstraints().add(row1);

                RowConstraints row2 = new RowConstraints();
                row2.setPrefHeight(30);
                row2.setMinHeight(10);
                row2.setVgrow(Priority.SOMETIMES);
                card_form.getRowConstraints().add(row2);

                RowConstraints row3 = new RowConstraints();
                row3.setPrefHeight(30);
                row3.setMinHeight(10);
                row3.setVgrow(Priority.SOMETIMES);
                card_form.getRowConstraints().add(row3);

                //First row
                Label bankLabel = new Label("Bank");
                bankLabel.getStyleClass().add("gridpane-label2");
                Label bank_name = new Label(cardinfo.getBank());
                bank_name.setPrefWidth(150);

                //Second row
                Label accountNumberLabel = new Label("Account no.");
                accountNumberLabel.setWrapText(true);
                accountNumberLabel.getStyleClass().add("gridpane-label2");
                Label accountNumber_cardForm = new Label(cardinfo.getAccountNumber() + "");

                //third row
                Label ibanLabel = new Label("IBAN");
                ibanLabel.getStyleClass().add("gridpane-label2");
                Label iban = new Label(cardinfo.getIBAN());

                //first column
                card_form.add(bankLabel, 0, 0);
                card_form.add(accountNumberLabel, 0, 1);
                card_form.add(ibanLabel, 0, 2);

                //second column
                card_form.add(bank_name, 1, 0);
                card_form.add(accountNumber_cardForm, 1, 1);
                card_form.add(iban, 1, 2);

                //add the label and grid pane to the box
                card.getChildren().addAll(cardLabel, card_form);

                //add the box to the bigger container
                cards_vbox.getChildren().addAll(card);

                //adjust the bigger container height
                cards_vbox.setPrefHeight(card.getPrefHeight() * cardInformation.size());

                //adjust the biggest container height
                driver_vbox.setPrefHeight(driver_vbox.getPrefHeight() + card.getPrefHeight() + 10);
            }
        }
    }
}
