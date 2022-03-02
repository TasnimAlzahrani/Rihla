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
import java.util.ArrayList;
import java.util.LinkedList;
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
import javafx.geometry.HPos;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author HU6EM001
 */
public class CreateDriverAccountController implements Initializable {

    @FXML
    private VBox driver_info_vbox;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private DatePicker birthDate;
    @FXML
    private ComboBox<String> nationality;
    @FXML
    private Button uploadAnImageBtn;
    @FXML
    private Slider feeSlider;
    @FXML
    private Label fee;
    @FXML
    private ComboBox<String> cities;
    @FXML
    private ListView<String> neighborhoods;
    @FXML
    private RadioButton cashOnly;
    @FXML
    private ToggleGroup paymentMethod;
    @FXML
    private RadioButton cash_and_card;
    @FXML
    private VBox credit_card_information_vbox;

    @FXML
    private VBox bank_forms_vbox;

    public static Driver driver;

    public static List<CardInformation> cardInformation = new ArrayList();

    public static ServedNeighborhood servedNeighborhood;

    public static ObservableList<String> neighborhoodsList;

    private byte[] buffer;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirm_password;

    @FXML
    private TextField email;

    public static ObservableList<String> servedNeighborhoods;

    public static String cityCode = "";

    private List<String> banks = new LinkedList<>();
    private List<TextField> accountNumbers = new LinkedList<>();
    private List<TextField> IBANs = new LinkedList<>();
    @FXML
    private TextField accountNumber;
    @FXML
    private TextField IBAN;

    ObservableList bank_names = FXCollections.observableArrayList("Al Rajhi", "Al Ahli", "Al Enmaa");

    @FXML
    private ComboBox<String> bank;
    @FXML
    private GridPane card_form;

    private int cardCount = 0;
    @FXML
    private Label you_have_entered;

    private Label accountNumber_cardForm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //if the user went to the next page and then came back
        if (driver != null) {
            // fill in his info
            fill();
            //set card form to be disabled
            card_form.setDisable(true);
            //if the user has less than 4 cards
            if (cardCount >= 4) {
                //don't disable the whole section
                credit_card_information_vbox.setVisible(false);
            }

        } else {
            //set the controlers values
            card_form.setDisable(true);
            nationality.setValue("choose a nationality");
            cities.setValue("choose a city");
            bank.setValue("Choose a bank");

        }

        //can choose more than one neighborhood
        neighborhoods.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //nationalities
        ObservableList< String> nationalities = FXCollections.observableArrayList("Saudi", "Egyptian", "Indian", "Sudanese", "Pakistani", "Indian", "Indonesian");

        nationality.setItems(nationalities);

        //banks
        bank.setItems(bank_names);

        //cities
        ObservableList<String> citiesList = FXCollections.observableArrayList("Makkah", "Jaddah");

        cities.setItems(citiesList);

        //listen for the price slider changes
        feeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                fee.setText("fee: " + String.format("%,.0f", feeSlider.getValue()) + "SR");
            }
        });

        //get the choosen neighborhoods
        neighborhoods.setOnMouseClicked(e -> {
            servedNeighborhoods = neighborhoods.getSelectionModel().getSelectedItems();
        });

    }

    //when combobox value changes -> change the neghbrhood value
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

    //when add a new card button is pressed
    @FXML
    private void enter_new_card(ActionEvent event) {
        //clear previous values
        accountNumber.clear();
        IBAN.clear();
        bank.setValue("choose a bank");
        //if the user has 4 cards -> can't add more
        if (cardCount >= 4) {
            Alert e = new Alert(Alert.AlertType.WARNING);
            e.setTitle("Be aware");
            e.setHeaderText(null);
            e.setContentText("You can't add more cards");
            e.show();

            credit_card_information_vbox.setVisible(false);

            // if the user has less than 4 allow to enter a new card
        } else {
            card_form.setDisable(false);
        }
    }

    @FXML
    private void hide_credit_card_information(ActionEvent event) {
        //when the driver accept cash only 
        //show an alert with cancel/yes buttons
        Alert e = new Alert(Alert.AlertType.WARNING);
        e.setTitle("Be aware");
        e.setHeaderText(null);
        e.setContentText("This will delete your cards information, do you want to delete it?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        e.getButtonTypes().setAll(yesButton, cancelButton);
        Optional<ButtonType> result = e.showAndWait();

        //if the user likes to delete them
        if (result.get() == yesButton) {
            //hide the cards section
            credit_card_information_vbox.setVisible(false);
            //empty the list of cards
            cardInformation.clear();
        } else {
            //deselect cash onlyb radio button
            cashOnly.setSelected(false);
            cash_and_card.setSelected(true);

        }
    }

    //when the user choose cash and credit card
    @FXML
    private void show_credit_card_information(ActionEvent event) {
        credit_card_information_vbox.setVisible(true);
    }

    @FXML
    private void uploadAnImage(ActionEvent event) {
        //open a file chooser dialog 
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        //show on the screen that has the button that fires the event
        File selectedImage = fileChooser.showOpenDialog(uploadAnImageBtn.getScene().getWindow());

        //if an image is selected after pressing the button
        if (selectedImage != null) {
            //set the buffer length to the image length
            buffer = new byte[(int) selectedImage.length()];
            try {
                //store the image in the buffer as byte array
                FileInputStream inputStream = new FileInputStream(selectedImage);
                inputStream.read(buffer);
                inputStream.close();

            } catch (IOException e) {
                //if the storing failed
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Something went wrong");
                error.setHeaderText(null);
                error.setContentText("Try again");
                error.show();
            }

        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nextPage = FXMLLoader.load(getClass().getResource("chooseAccountType.fxml"));
        Scene next = new Scene(nextPage);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(next);
        stage.show();
    }

    @FXML
    private void goForward(ActionEvent event) throws IOException {
        //check email
        String domain = "";
        if (email.getText().contains("@")) {
            int index = email.getText().indexOf("@");
            domain = email.getText().substring(index);
        }
        //check if any field/the buffer is empty
        if (firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || birthDate == null
                || email.getText().isEmpty()
                || password.getText().isEmpty()
                || fee.getText().isEmpty()
                || nationality.getValue().equals("choose a nationality")
                || neighborhoods.getSelectionModel().getSelectedItems().isEmpty()
                || cities.getValue().equals("choose a city")
                || buffer == null) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please fill all the fields");
            error.show();

            // if the password != password confirmation
        } else if (!password.getText().equals(confirm_password.getText())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Please enter matching passwords");
            error.show();
            //if phone number is not 10 digits
        } else if (phoneNumber.getText().length() != 10) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("WARNING");
            error.setHeaderText(null);
            error.setContentText("The length of Mobile number must be 10 digits");
            error.show();

            //if email is not correct
        } else if (!(domain.equals("@gmail.com")) && !(domain.equals("@st.uqu.edu.sa")) && !(domain.equals("@yahoo.com")) && !(domain.equals("@outlook.com")) && !(domain.equals("@hotmail.com")) && !(domain.equals("@live.com"))) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("WARNING");
            error.setHeaderText(null);
            error.setContentText("Please enter a valid e-mail");
            error.show();

            //otherwise
        } else {

            //craete driver object and store it in the database
            driver = new Driver();
            driver.setName(firstName.getText() + " " + lastName.getText());
            driver.setDateOfbirth(birthDate.getValue() + "");
            driver.setEmail(email.getText());
            driver.setPassword(password.getText());
            driver.setFee(feeSlider.getValue());
            driver.setNationality(nationality.getValue());
            driver.setServedCity(cities.getValue());
            driver.setPhone_number(phoneNumber.getText());
            driver.setPhoto(buffer);

            //set city code for storing the neighborhoods
            if (cities.getValue().equals("Makkah")) {
                cityCode = "1";
            } else {
                cityCode = "2";
            }

            Parent nextPage = FXMLLoader.load(getClass().getResource("createDriverAccount_busInformation.fxml"));
            Scene next = new Scene(nextPage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(next);
            stage.show();
        }

    }

    private void showCards() {
        //remove any thing in the box to avoid repreating items
        bank_forms_vbox.getChildren().removeAll(bank_forms_vbox.getChildren());

        //for every card
        for (CardInformation cardinfo : cardInformation) {

            //craete a form
            VBox card = new VBox(15);
            card.setPrefSize(320, 146);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(0, 20, 0, 20));
//            card.getStyleClass().add("card");
            //set the css
            card.setStyle("-fx-background-color:#f4eed477; -fx-background-radius: 5px; ");

            //form label
            Label cardLabel = new Label("Card information");
            cardLabel.setTextAlignment(TextAlignment.CENTER);
            cardLabel.setPrefHeight(17);
            cardLabel.setPrefWidth(333);
            cardLabel.getStyleClass().add("gridpane-label2");
            cardLabel.setAlignment(Pos.CENTER);

            //craete a grid
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
                //get the account number that equals the account number in this box
                for (int i = 0; i < cardInformation.size(); i++) {
                    int accNum = cardInformation.get(i).getAccountNumber();
                    if (accountNumber_cardForm.getText().equals(Integer.toString(accNum))) {
                        //remove it from the list
                        cardInformation.remove(i);
                    }
                }
                //adjust the screen height
                bank_forms_vbox.getChildren().removeAll(card);
                cardCount -= 1;
                switch (cardCount) {
                    case 0:
                        driver_info_vbox.setPrefHeight(1700);
                        break;
                    case 1:
                        driver_info_vbox.setPrefHeight(1800);
                        break;
                    case 2:
                        driver_info_vbox.setPrefHeight(1900);
                        break;
                    case 3:
                        driver_info_vbox.setPrefHeight(2000);
                        break;
                    case 4:
                        driver_info_vbox.setPrefHeight(2100);
                        break;
                }

            });

            //add to the bigger vbox
            card.getChildren().addAll(cardLabel, remove, card_form);

            //add to the biggest vbox
            bank_forms_vbox.getChildren().addAll(card);

        }
        //resize
        switch (cardCount) {
            case 0:
                driver_info_vbox.setPrefHeight(1700);
                break;
            case 1:
                driver_info_vbox.setPrefHeight(1800);
                break;
            case 2:
                driver_info_vbox.setPrefHeight(1900);
                break;
            case 3:
                driver_info_vbox.setPrefHeight(2000);
                break;
            case 4:
                driver_info_vbox.setPrefHeight(2100);
                break;
        }
    }

    //fill in the information when the driver come back
    private void fill() {
        firstName.setText(driver.getName().substring(0, driver.getName().indexOf(' ')));

        lastName.setText(driver.getName().substring(driver.getName().indexOf(' ') + 1));

        birthDate.setValue(LocalDate.parse(driver.getDateOfbirth()));

        email.setText(driver.getEmail());

        fee.setText(Double.toString(driver.getFee()));

        nationality.setValue(driver.getNationality());

        cities.setValue(driver.getServedCity());

        if (cities.getValue().equals("Makkah")) {
            neighborhoodsList = FXCollections.observableArrayList("Alawali", "Alsharaya", "Alzahir", "Alzaidee", "Al Eskan", "Azizyah", "Al shawqiyyah", "Al shumaisi", "Al hejra", "Al khadra", "Al hamra", "Al nuzha");
        } else {

            neighborhoodsList = FXCollections.observableArrayList("AlNaseem", "AlRabua", "AlNuzha", "AlHamra", "AlMurjan", "AlKhlidia", "AlRehab", "AlManar", "AlAndalus", "AlRabwa", "AlRawabi", "Albasateen");
        }

        phoneNumber.setText(driver.getPhone_number());
        showCards();
    }

    //when click on submit form
    @FXML
    private void submitCardForm(ActionEvent event) {
        //if the fields are empty
        if (accountNumber.getText().isEmpty()
                || bank.getValue().equals("choose a bank")
                || IBAN.getText().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Fill in all the fields");
            error.setHeaderText(null);
            error.setContentText("Try again");
            error.show();

        } else {
            //increment cards number
            cardCount += 1;
            //add the card to the cards list
            cardInformation.add(new CardInformation(Integer.parseInt(accountNumber.getText()),
                    IBAN.getText(),
                    bank.getValue()));

            //disable the form
            card_form.setDisable(true);

            //show all the cards in the list
            showCards();
        }

    }
}
