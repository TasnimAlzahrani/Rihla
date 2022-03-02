/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class NoAssignedBusController implements Initializable {

    @FXML
    private VBox container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView no_missing_items = new ImageView(new Image("nothing-here.png"));
        no_missing_items.setPreserveRatio(true);
        no_missing_items.setFitWidth(300);

        Label no_missing_itemsLabel = new Label("You are not assigned to any bus");
        no_missing_itemsLabel.getStyleClass().add("gridpane-label");
        container.getChildren().addAll(no_missing_items, no_missing_itemsLabel);
    }

    @FXML
    private void goBack(ActionEvent event) {
    }

}
