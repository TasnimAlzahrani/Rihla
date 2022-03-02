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
import javafx.scene.input.MouseEvent;
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
public class MissingItems_driverController implements Initializable {

    @FXML
    private VBox container;

    /**
     * Initializes the controller class.
     */
    private Session session1;
    private Transaction transaction;
    private Query query;

    public void initialize(URL url, ResourceBundle rb) {

        session1 = HibernateUtil.getSessionFactory().openSession();
        transaction = session1.beginTransaction();
        String queryStr = "from MissingItem where driverId = " + LoginController.driver.getDriverId();
        query = session1.createQuery(queryStr);
        List<MissingItem> missing_list = query.list();
        System.out.println(missing_list.size());
        transaction.commit();
        session1.close();

        container.setSpacing(10);

        if (!missing_list.isEmpty()) {
            container.setAlignment(Pos.TOP_CENTER);

            for (MissingItem missingItem : missing_list) {

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
                itemName.getStyleClass().add("gridpane-label");
                itemName.setText(missingItem.getMissingItemLabel());

                TextArea itemDescrip = new TextArea();
                itemDescrip.setEditable(false);
                itemDescrip.setId("itemDescrip");
                itemDescrip.setText(missingItem.getMissingItemDescription());

                items.add(itemName, 1, 0);
                items.add(itemDescrip, 1, 1);

                //render a remove button
                ImageView removeImageView = new ImageView(new Image("trash.png"));
                removeImageView.setFitWidth(37);
                removeImageView.setFitHeight(26);
                removeImageView.setPreserveRatio(true);
                Button remove = new Button("", removeImageView);
                remove.setMnemonicParsing(false);
                remove.getStyleClass().add("smallbtn");
                remove.setStyle("-fx-background-color: #db7c7c;");
                //on click
                remove.setOnAction(e -> {
                    //go to the database
                    session1 = HibernateUtil.getSessionFactory().openSession();
                    transaction = session1.beginTransaction();
                    //delete the missing item object
                    session1.delete(missingItem);
                    //and the remove it from the screen
                    container.getChildren().remove(studentItem);

                    //check if there is any missing items left with this driver id
                    query = session1.createQuery("from MissingItem where driverId = " + LoginController.driver.getDriverId());
                    if (query.list().isEmpty()) {
                        //if no -> display an image and "no missing items" label
                        noMissingItems();
                    }
                    transaction.commit();
                    session1.close();

                });

                studentItem.getChildren().addAll(items, remove);

                container.getChildren().add(studentItem);
            }
        } else {

            //show image no missing items
            noMissingItems();

        }
    }

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
    private void report(ActionEvent event) throws IOException {
        Parent forgetpassword = FXMLLoader.load(getClass().getResource("report.fxml"));
        Scene forgetpasswordscene = new Scene(forgetpassword);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(forgetpasswordscene);
        stage.show();
    }

    //show no missing items image
    private void noMissingItems() {
        container.setAlignment(Pos.CENTER);
        container.setSpacing(5);
        ImageView no_missing_items = new ImageView(new Image("missing-item.png"));
        no_missing_items.setPreserveRatio(true);
        no_missing_items.setFitWidth(300);

        Label no_missing_itemsLabel = new Label("No missing items found");
        no_missing_itemsLabel.getStyleClass().add("gridpane-label");
        container.getChildren().addAll(no_missing_items, no_missing_itemsLabel);
    }

}
