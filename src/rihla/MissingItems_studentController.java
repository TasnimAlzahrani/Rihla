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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class MissingItems_studentController implements Initializable {

    /**
     * Initializes the controller class.
     *
     */
    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (LoginController.student.getDriverId() == null) {

            ImageView no_driver = new ImageView(new Image("nothing-here.png"));
            no_driver.setPreserveRatio(true);
            no_driver.setFitWidth(300);

            Label no_driverLabel = new Label("You are not assigned to any bus");
            no_driverLabel.getStyleClass().add("gridpane-label");
            Button b = new Button();
            b.setText("Register in a Bus");
            b.setOnAction((ActionEvent e) -> {
                try {
                    changeBusb(e);
                } catch (IOException ex) {
                    Logger.getLogger(MissingItems_studentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            );

            container.getChildren().addAll(no_driver, no_driverLabel, b);
            container.setMargin(b, new Insets(15, 0, 0, 0));
            container.setMargin(no_driver, new Insets(0, 0, 0, 0));

        } else {

            container.setSpacing(10);

            Session session1 = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session1.beginTransaction();
            String queryStr = "from MissingItem where driverId = " + LoginController.student.getDriverId();
            Query query = session1.createQuery(queryStr);
            List<MissingItem> missing_list = query.list();
            transaction.commit();
            session1.close();

            if (!missing_list.isEmpty()) {
                for (MissingItem m : missing_list) {

                    container.setAlignment(Pos.TOP_CENTER);
                    VBox studentItem = new VBox();

                    studentItem.setPrefSize(271, 122);
                    studentItem.setSpacing(10);
                    studentItem.setPadding(new Insets(20, 20, 20, 20));
                    //styling the vbox
                    studentItem.setStyle("-fx-background-color:#f4eed477; -fx-background-radius: 5px; ");

                    GridPane items = new GridPane();
                    items.setHgap(22);
                    items.setVgap(9);
                    ColumnConstraints column1 = new ColumnConstraints();
                    column1.setHalignment(HPos.LEFT);
                    items.getColumnConstraints().add(column1);

                    Label itemName = new Label();
                    itemName.getStyleClass().add("Label");
                    itemName.setText(m.getMissingItemLabel());

                    TextArea itemDescrip = new TextArea();
                    itemDescrip.setEditable(false);
                    itemDescrip.setId("itemDescrip");
                    itemDescrip.setText(m.getMissingItemDescription());

                    items.add(itemName, 1, 0);
                    items.add(itemDescrip, 1, 1);

                    studentItem.getChildren().addAll(items);

                    container.getChildren().add(studentItem);
                }
            } else {
                container.setAlignment(Pos.CENTER);
                container.setSpacing(5);
                ImageView no_missing_items = new ImageView(new Image("missing-item.png"));
                no_missing_items.setPreserveRatio(true);
                no_missing_items.setFitWidth(300);

                Label no_missing_itemsLabel = new Label("No missing items found");
                no_missing_itemsLabel.getStyleClass().add("gridpane-label");
                container.getChildren().addAll(no_missing_items, no_missing_itemsLabel);
                //show image no missing items

            }
        }
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

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("assignedBus.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    @FXML
    private void missingItems(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("missingItems_student.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    private void changeBusb(ActionEvent event) throws IOException {

        Parent forgetpassword = FXMLLoader.load(getClass().getResource("changeBus.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }
}
