/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class Edit_info_driverController implements Initializable {

    @FXML
    private VBox driver_info_vbox;

    @FXML
    private VBox bank_forms_vbox;

    @FXML
    private ToggleGroup paymentMethod;

    @FXML
    private VBox credit_card_information_vbox;

    @FXML
    private RadioButton cashOnly;

    @FXML
    private RadioButton cash_and_card;

    @FXML
    private ComboBox cities;

    @FXML
    private ListView neighborhoods;

    @FXML
    private ComboBox nationality;

    @FXML
    private Button uploadAnImageBtn;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private DatePicker birthDate;
    @FXML
    private Slider feeSlider;
    @FXML
    private Label fee;

    private ObservableList<String> neighborhoodsList;

    private Session session;

    private Transaction tx;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField email;
    @FXML
    private GridPane card_form;
    @FXML
    private TextField accountNumber;
    @FXML
    private TextField IBAN;
    @FXML
    private ComboBox<String> bank;
    @FXML
    private Label current_served_neighborhoods;

    private int cardCount;

    private ObservableList bank_names = FXCollections.observableArrayList("Al Rajhi", "Al Ahli", "Al Enmaa");

    @FXML
    private Label you_have_entered;

    private Label accountNumber_cardForm;

    private List<CardInformation> cardInformation;

    public static ObservableList<String> servedNeighborhoods = FXCollections.observableArrayList();

    private List<ServedNeighborhood> servedNeighborhoodsDB;

    private List<Neighborhood> neighborhoodsDB;

    private String cityCode = "";

    private ServedNeighborhood servedNeighborhood;

    private byte[] buffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        firstName.setText(LoginController.driver.getName().substring(0, LoginController.driver.getName().indexOf(' ')));

        lastName.setText(LoginController.driver.getName().substring(LoginController.driver.getName().indexOf(' ') + 1));

        birthDate.setValue(LocalDate.parse(LoginController.driver.getDateOfbirth()));

        email.setText(LoginController.driver.getEmail());

        fee.setText(Double.toString(LoginController.driver.getFee()));

        ObservableList<String> nationalities = FXCollections.observableArrayList("Saudi");
        nationality.setItems(nationalities);
        nationality.setValue(LoginController.driver.getNationality());

        cities.setValue(LoginController.driver.getServedCity());

        if (cities.getValue().equals("Makkah")) {
            neighborhoodsList = FXCollections.observableArrayList("AlAwali", "AlSharaya", "AlHamra", "AlNuzha", "AlZahir", "AlZaidee", "AlEskan", "Azizyah", "AlShawqiyyah", "AlShumaisi", "AlHejra", "AlKhadra");
        } else {

            neighborhoodsList = FXCollections.observableArrayList("AlNaseem", "AlRabua", "AlNuzha", "AlHamra", "AlMurjan", "AlKhlidia", "AlRehab", "AlManar", "AlAndalus", "AlRabwa", "AlRawabi", "Albasateen");
        }
        neighborhoods.setItems(neighborhoodsList);
        neighborhoods.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String tempValue = "";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From ServedNeighborhood where driverId=" + LoginController.driver.getDriverId());
        servedNeighborhoodsDB = query.list();

        query = session.createQuery("From Neighborhood");
        neighborhoodsDB = query.list();

        for (int i = 0; i < servedNeighborhoodsDB.size(); i++) {
            for (int j = 0; j < neighborhoodsDB.size(); j++) {
                if (neighborhoodsDB.get(j).getNeighborhoodId().equals(servedNeighborhoodsDB.get(i).getNeighborhoodId())) {

                    tempValue += " " + neighborhoodsDB.get(i).getNeighborhoodName();
                }
            }
        }

        query = session.createQuery("From CardInformation where driverId=" + LoginController.driver.getDriverId());
        cardInformation = query.list();

        transaction.commit();
        session.close();

        cardCount = cardInformation.size();

        card_form.setDisable(true);
        if (cardCount >= 4) {
            credit_card_information_vbox.setVisible(false);
        }

        current_served_neighborhoods.setText(tempValue);

        showCards();

        phoneNumber.setText(LoginController.driver.getPhone_number());

        ObservableList<String> citiesList = FXCollections.observableArrayList("Makkah", "Jaddah");
        cities.setItems(citiesList);

        feeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                fee.setText("fee: " + String.format("%,.0f", feeSlider.getValue()) + "SR");
            }
        });

        neighborhoods.getSelectionModel().selectedItemProperty().addListener(e -> {
            servedNeighborhoods = neighborhoods.getSelectionModel().getSelectedItems();
        });

    }

    @FXML
    private void enter_new_card(ActionEvent event) {
        accountNumber.clear();
        IBAN.clear();
        bank.setValue("choose a bank");
        if (cardCount >= 4) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setTitle("Be aware");
            e.setHeaderText(null);
            e.setContentText("You can't add more cards");
            e.show();

            credit_card_information_vbox.setVisible(false);

        } else {
            card_form.setDisable(false);
        }
    }

    @FXML
    private void hide_credit_card_information(ActionEvent event) {
        Alert e = new Alert(Alert.AlertType.WARNING);
        e.setTitle("Be aware");
        e.setHeaderText(null);
        e.setContentText("This will delete your cards information, do you want to delete it?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        e.getButtonTypes().setAll(yesButton, cancelButton);
        Optional<ButtonType> result = e.showAndWait();

        if (result.get() == yesButton) {
            credit_card_information_vbox.setVisible(false);
            cardInformation.clear();
        } else {
            cashOnly.setSelected(false);
            cash_and_card.setSelected(true);

        }
    }

    @FXML
    private void show_credit_card_information(ActionEvent event) {
        credit_card_information_vbox.setVisible(true);
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        if (firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || birthDate == null
                || email.getText().isEmpty()
                || password.getText().isEmpty()
                || fee.getText().isEmpty()
                || nationality.getValue().equals("choose a nationality")
                || cities.getValue().equals("choose a city")) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill all the fields");
            error.show();

        } else if (!password.getText().equals(confirm_password.getText())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please enter matching passwords");
            error.show();
        } else {

            LoginController.driver.setName(firstName.getText() + " " + lastName.getText());
            LoginController.driver.setDateOfbirth(birthDate.getValue() + "");
            LoginController.driver.setEmail(email.getText());
            LoginController.driver.setPassword(password.getText());
            LoginController.driver.setFee(feeSlider.getValue());
            LoginController.driver.setNationality((String) nationality.getValue());
            LoginController.driver.setServedCity((String) cities.getValue());
            LoginController.driver.setPhone_number(phoneNumber.getText());

            if (buffer != null) {
                LoginController.driver.setPhoto(buffer);
            }

            if (cities.getValue().equals("Makkah")) {
                cityCode = "1";
            } else {
                cityCode = "2";
            }
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            if (Edit_info_busController.changeBus) {
                session.update(Edit_info_busController.bus);
            }

            if (Edit_info_busController.changeTripsFromCampus) {
                Query query = session.createQuery("delete from TripsSchedule_fromCampus where busId= " + LoginController.driver.getBusId());
                query.executeUpdate();

                for (String time : Edit_info_busController.tripsTimes_fromCampus) {
                    TripsSchedule_fromCampus tripsSchedule_fromCampus = new TripsSchedule_fromCampus();
                    tripsSchedule_fromCampus.setBusId(LoginController.driver.getBusId());
                    tripsSchedule_fromCampus.setFromCampusTime(time);
                    session.save(tripsSchedule_fromCampus);
                }

            }

            if (Edit_info_busController.changeTripsToCampus) {
                Query query = session.createQuery("delete from TripsSchedule_toCampus where busId= " + LoginController.driver.getBusId());
                query.executeUpdate();

                for (String time : Edit_info_busController.tripsTimes_toCampus) {
                    TripsSchedule_toCampus tripsSchedule_toCampus = new TripsSchedule_toCampus();
                    tripsSchedule_toCampus.setBusId(LoginController.driver.getBusId());
                    tripsSchedule_toCampus.setToCampusTime(time);
                    session.save(tripsSchedule_toCampus);
                }

            }
            session.update(LoginController.driver);

            if (!servedNeighborhoods.isEmpty()) {

                Query query = session.createQuery("delete from ServedNeighborhood where driverId= " + LoginController.driver.getDriverId());
                query.executeUpdate();

                for (String string : servedNeighborhoods) {
                    servedNeighborhood = new ServedNeighborhood();
                    servedNeighborhood.setDriverId(LoginController.driver.getDriverId());
                    servedNeighborhood.setNeighborhoodId(cityCode + neighborhoodsList.indexOf(string));
                    session.save(servedNeighborhood);
                }
            }
            tx.commit();
            session.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("driver_myProfile.fxml"));
            Parent nextPage = loader.load();
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }
    }

    @FXML
    private void uploadAnImage(ActionEvent event
    ) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedImage = fileChooser.showOpenDialog(uploadAnImageBtn.getScene().getWindow());
        if (selectedImage != null) {
            buffer = new byte[(int) selectedImage.length()];
            try {

                FileInputStream inputStream = new FileInputStream(selectedImage);
                inputStream.read(buffer);
                inputStream.close();

            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Something went wrong");
                error.setHeaderText(null);
                error.setContentText("Try again");
                error.show();
            }

        }
    }

    @FXML
    private void setNeighborhoods(ActionEvent event
    ) {
        if (cities.getValue().equals("Makkah")) {
            neighborhoodsList = FXCollections.observableArrayList("AlAwali", "AlSharaya", "AlHamra", "AlNuzha", "AlZahir", "AlZaidee", "AlEskan", "Azizyah", "AlShawqiyyah", "AlShumaisi", "AlHejra", "AlKhadra");
        } else {

            neighborhoodsList = FXCollections.observableArrayList("AlNaseem", "AlRabua", "AlNuzha", "AlHamra", "AlMurjan", "AlKhlidia", "AlRehab", "AlManar", "AlAndalus", "AlRabwa", "AlRawabi", "Albasateen");
        }
        neighborhoods.setItems(neighborhoodsList);
    }

    @FXML
    private void editBusInformation(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("edit_info_bus.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    private void resize() {

        switch (cardCount) {

            case 0:
                driver_info_vbox.setPrefHeight(1700);
                break;
            case 1:
                driver_info_vbox.setPrefHeight(2100);

                break;
            case 2:
                driver_info_vbox.setPrefHeight(2200);
                break;
            case 3:
                driver_info_vbox.setPrefHeight(2300);
                break;
            case 4:
                driver_info_vbox.setPrefHeight(2400);
                break;
        }

    }

    private void showCards() {
        bank_forms_vbox.getChildren().removeAll(bank_forms_vbox.getChildren());
        bank.setItems(bank_names);

        for (CardInformation cardinfo : cardInformation) {

            VBox card = new VBox(15);
            card.setPrefSize(320, 146);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(0, 20, 0, 20));
            card.getStyleClass().add("card");

            Label cardLabel = new Label("Card information");
            cardLabel.setTextAlignment(TextAlignment.CENTER);
            cardLabel.setPrefHeight(17);
            cardLabel.setPrefWidth(333);
            cardLabel.getStyleClass().add("gridpane-label2");
            cardLabel.setAlignment(Pos.CENTER);

            GridPane card_form = new GridPane();
            card_form.setVgap(5);
            card_form.setPrefSize(296, 52);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPrefWidth(90);
            column1.setMinWidth(10);
            column1.setMaxWidth(138);
            column1.setHgrow(Priority.SOMETIMES);
            card_form.getColumnConstraints().add(column1);

            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPrefWidth(198);
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
            accountNumber_cardForm = new Label(cardinfo.getAccountNumber() + "");

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

            ImageView removeImageView = new ImageView(new Image("trash.png"));
            removeImageView.setFitWidth(37);
            removeImageView.setFitHeight(26);
            removeImageView.setPreserveRatio(true);
            Button remove = new Button("", removeImageView);
            remove.setMnemonicParsing(false);
            remove.getStyleClass().add("smallbtn");
            remove.setStyle("-fx-background-color: #db7c7c;");

            remove.setOnAction(e -> {

                for (int i = 0; i < cardInformation.size(); i++) {
                    int accNum = cardInformation.get(i).getAccountNumber();
                    if (accountNumber_cardForm.getText().equals(Integer.toString(accNum))) {

                        Session session = HibernateUtil.getSessionFactory().openSession();
                        Transaction transaction = session.beginTransaction();
                        session.delete(cardInformation.get(i));
                        transaction.commit();
                        session.close();

                        cardInformation.remove(i);
                    }
                }
                bank_forms_vbox.getChildren().removeAll(card);
                cardCount -= 1;
                resize();
            });

            card.getChildren().addAll(cardLabel, remove, card_form);

            bank_forms_vbox.getChildren().addAll(card);
        }
        resize();
    }

    @FXML
    private void submitCardForm(ActionEvent event) {
        if (accountNumber.getText().isEmpty()
                || bank.getValue().equals("choose a bank")
                || IBAN.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Fill in all the fields");
            error.setHeaderText(null);
            error.setContentText("Try again");
            error.show();
        } else {
            cardCount += 1;
            cardInformation.add(new CardInformation(Integer.parseInt(accountNumber.getText()),
                    IBAN.getText(),
                    bank.getValue()));
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.save(new CardInformation(LoginController.driver.getDriverId(),
                    Integer.parseInt(accountNumber.getText()),
                    IBAN.getText(),
                    bank.getValue()));
            transaction.commit();
            session.close();

            System.out.println(cardInformation.toString());
            if (cardCount == 0) {
                card_form.setDisable(true);
            } else {
                card_form.setDisable(true);
            }
            showCards();
        }

    }

}
