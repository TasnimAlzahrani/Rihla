/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
public class AvailableBusesController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox container;                       // neighborhood - city - nationality - fee - age 

    @FXML                                         // neighborhood -> get servedNeighborhood 
    private VBox containerFilter;                 //age -> calculate age using localdate 
    @FXML                                         // neighborhood -> get servedNeighborhood 
    private VBox containerBuses;

    @FXML
    private ComboBox<String> filters;

    List<String> tolist3 = new ArrayList<String>();
    List<String> fromlist3 = new ArrayList<String>();

    //List<Driver> dlist = new ArrayList<Driver>();
    Slider age = new Slider();
    TextField fee = new TextField();
    ComboBox<String> nation = new ComboBox();
    ComboBox<String> neighbor = new ComboBox();
    RadioButton makkah = new RadioButton("Makkah");
    RadioButton other = new RadioButton("Other");
    ToggleGroup group = new ToggleGroup();

    /**
     * Initializes the controller class.
     */
    int driverIdd = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filters.getItems().addAll("All", "Age", "Nationality", "Fee");
        container.setSpacing(20);
        containerBuses.getChildren().clear();
    }

    @FXML
    private void filter(ActionEvent event) throws IOException, ParseException {
        System.out.println("here");
        containerFilter.getChildren().clear();
        String choice = filters.getValue();
        switch (choice) {
            case "All": {
                Session session = HibernateUtil.getSessionFactory().openSession();
                String queryStr3 = "from Driver";
                Query query3 = session.createQuery(queryStr3);
                List<Driver> dlist = query3.list();
                session.close();
                System.out.println("list size: " + dlist.size());
                for (Driver d : dlist) {
                    System.out.println(d.getName() + " - " + d.getDriverId());
                }
                showBuses(dlist);

                break;

            }
            case "Age": {

                age.setMin(20);
                age.setMax(60);
                age.setValue(40);//the current value
                age.setShowTickLabels(true);//showing the numbers as labels or not
                age.setShowTickMarks(true);//showing the ticks mark or not
                age.setMajorTickUnit(5);//where to put major ticks
                age.setMinorTickCount(1);//the number of minor ticks in the slider
                age.setBlockIncrement(1);//how many positions the pointer is moved

                Label ageLabel = new Label();

                age.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldNumber, Number newNumber) {

                        fee.setText("Age: " + Integer.toString((int) age.getValue()));
                    }
                });

                Button ageB = new Button();
                ageB.setText("Ok");
                ageB.setAlignment(Pos.CENTER);

                ageB.setOnAction(e -> {
                    try {
                        agefilter();
                        System.out.println("entered");
                    } catch (ParseException ex) {
                        Logger.getLogger(AvailableBusesController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("failed");
                    }
                });

                containerFilter.getChildren().addAll(ageLabel, age);
                containerFilter.getChildren().add(ageB);
                containerFilter.setMargin(ageB, new Insets(10, 0, 0, 55));
                containerFilter.setMargin(age, new Insets(020, 0, 0, 0));
                break;
            }
            case "Nationality": {
                Label natLabel = new Label("Choose:");
                natLabel.getStyleClass().add("gridpane-data");

                nation.setPrefWidth(160);
                nation.setPrefHeight(35);
                nation.getItems().addAll("Saudi", "Egyptian", "Indian", "Sudanese", "Pakistani", "Indian", "Indonesian");
                nation.setOnAction(e -> {
                    try {
                        nationfilter();
                    } catch (ParseException ex) {
                        Logger.getLogger(AvailableBusesController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("failed");
                    }
                });

                HBox box2 = new HBox(13);
                box2.getChildren().add(natLabel);
                box2.getChildren().add(nation);
                containerFilter.getChildren().add(box2);
                box2.setMargin(natLabel, new Insets(8, 0, 0, 10));
                box2.setMargin(nation, new Insets(8, 0, 0, 0));
                containerFilter.setMargin(box2, new Insets(8, 0, 0, 0));
                break;
            }
            case "Fee": {
                fee.setText("");
                fee.setPromptText("Enter fee");

                Button feeB = new Button();
                feeB.setText("Ok");
                feeB.setAlignment(Pos.CENTER);

                feeB.setOnAction(e -> {
                    try {
                        feefilter();
                        System.out.println("entered");
                    } catch (ParseException ex) {
                        Logger.getLogger(AvailableBusesController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("failed");
                    }
                });

                HBox box = new HBox(15);
                box.getChildren().add(fee);
                box.getChildren().add(feeB);
                containerFilter.getChildren().add(box);
                containerFilter.setMargin(box, new Insets(20, 0, 0, 0));
                box.setMargin(fee, new Insets(5, 0, 0, 0));
                break;
            }

            case "-select-": {
                break;
            }

        }

    }

    private void agefilter() throws ParseException {
        containerBuses.getChildren().removeAll(containerBuses.getChildren());
        containerBuses.getChildren().clear();
        List<Driver> dlist2 = new ArrayList<Driver>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String queryStr3 = "from Driver";
        Query query3 = session.createQuery(queryStr3);
        dlist2 = query3.list();
        session.close();

        List<Driver> list4 = new ArrayList<>();
        for (Driver d3 : dlist2) {
            String calculated_age = Integer.toString(Period.between(LocalDate.parse(d3.getDateOfbirth()), LocalDate.now()).getYears());
            System.out.println(Integer.parseInt(calculated_age) == age.getValue());
            if (Integer.parseInt(calculated_age) == age.getValue()) {
                list4.add(d3);
            }
        }

        for (Driver d : list4) {
            System.out.println(d.getName() + " - " + d.getDriverId());
        }

        System.out.println("not here");
        showBuses(list4);

    }

    private void nationfilter() throws ParseException {
        containerBuses.getChildren().removeAll(containerBuses.getChildren());
        containerBuses.getChildren().clear();
        List<Driver> dlist2 = new ArrayList<Driver>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String queryStr3 = "from Driver";
        Query query3 = session.createQuery(queryStr3);
        dlist2 = query3.list();
        session.close();
        System.out.println("list size: " + dlist2.size());
        for (Driver d : dlist2) {
            System.out.println(d.getName() + " - " + d.getDriverId());
        }

        List<Driver> list4 = new ArrayList<Driver>();
        for (Driver d3 : dlist2) {
            String n2 = nation.getValue();
            if (n2.toLowerCase().equals(d3.getNationality().toLowerCase())) {
                list4.add(d3);
            }
        }

        for (Driver d : list4) {
            System.out.println(d.getName() + " - " + d.getDriverId());
        }

        System.out.println("not here");
        showBuses(list4);

    }

    private void neighborhoods() throws ParseException {
        if (makkah.isSelected()) {

        }

    }

    private void feefilter() throws ParseException {
        containerBuses.getChildren().clear();
//        containerBuses.getChildren().clear();
        List<Driver> dlist2 = new ArrayList<Driver>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String queryStr3 = "from Driver";
        Query query3 = session.createQuery(queryStr3);
        dlist2 = query3.list();
        session.close();
        System.out.println("list size: " + dlist2.size());
        for (Driver d : dlist2) {
            System.out.println(d.getName() + " - " + d.getDriverId());
        }

        List<Driver> list4 = new ArrayList<Driver>();
        for (Driver d3 : dlist2) {
            int fee2 = (int) d3.getFee();

            System.out.println(fee2);
            if (fee2 == Integer.parseInt(fee.getText())) {
                list4.add(d3);
            }
        }

        for (Driver d : list4) {
            System.out.println(d.getName() + " - " + d.getDriverId());
        }

        System.out.println("not here");
        showBuses(list4);

    }

    String totime = "";
    String fromtime = "";

    private void showBuses(List<Driver> dlist) throws ParseException {
        containerBuses.getChildren().removeAll(containerBuses.getChildren());
        if (dlist.isEmpty()) {

            Label no = new Label("No available buses");
            no.getStyleClass().add("gridpane-label");
            containerBuses.getChildren().add(no);
            VBox.setMargin(no, new Insets(30, 0, 0, 75));
        } else {

            int i = 0;
            for (Driver d : dlist) {

                if (!d.getServedCity().equals(CreateStudentAccountController.studentAddress.getCity())) {
                    continue;
                }

                List<String> driverNeighborhoods = new ArrayList();

                Session session = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();
                Query query = session.createQuery("From ServedNeighborhood where driverId=" + d.getDriverId());
                List<ServedNeighborhood> servedNeighborhoodsDB = query.list();

                query = session.createQuery("From Neighborhood");
                List<Neighborhood> neighborhoodsDB = query.list();

                transaction.commit();
                session.close();

                for (int s = 0; s < servedNeighborhoodsDB.size(); s++) {
                    for (int j = 0; j < neighborhoodsDB.size(); j++) {
                        if (neighborhoodsDB.get(j).getNeighborhoodId().equals(servedNeighborhoodsDB.get(s).getNeighborhoodId())) {

                            System.out.println(neighborhoodsDB.get(s).getNeighborhoodName());
                            driverNeighborhoods.add(neighborhoodsDB.get(s).getNeighborhoodName());
                        }
                    }
                }
//
//                if (!driverNeighborhoods.contains(CreateStudentAccountController.studentAddress.getNeighborhood())) {
//                    System.out.println("LOL");
//                    continue;
//                }

                session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Query querySelect = session.createQuery("from Driver where driverId=" + d.getDriverId());
                Driver driver = (Driver) querySelect.uniqueResult();

                querySelect = session.createQuery("from Bus where busId=" + driver.getBusId());
                Bus bus = (Bus) querySelect.uniqueResult();
                transaction.commit();
                session.close();

                Label name = new Label(d.getName());
                name.getStyleClass().add("gridpane-label");

                FileOutputStream fos;
                try {
                    fos = new FileOutputStream("output" + i + ".jpg");
                    fos.write(d.getPhoto());
                    fos.close();
                } catch (IOException e) {

                }
                ImageView img = new ImageView(new Image("file:output" + i + ".jpg", 70, 70, false, false));
                AnchorPane top = new AnchorPane();
                top.setRightAnchor(img, 1.0);
                top.setLeftAnchor(name, 1.0);
                top.setTopAnchor(name, 12.0);
                top.getChildren().add(name);
                top.getChildren().add(img);

                GridPane info = new GridPane();
                info.setHgap(35);
                info.setVgap(8);
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setHalignment(HPos.LEFT);
                info.getColumnConstraints().add(column1);
                ColumnConstraints column2 = new ColumnConstraints();
                column2.setHalignment(HPos.LEFT);
                info.getColumnConstraints().add(column2);

                info.add(new Label("Age:"), 0, 0);
                String calculated_age = Integer.toString(Period.between(LocalDate.parse(d.getDateOfbirth()), LocalDate.now()).getYears());
                info.add(new Label(calculated_age), 0, 1);
                info.add(new Label("Bus Owner:"), 0, 2);
                info.add(new Label(bus.getOwner()), 0, 3);
                info.add(new Label("Licence No.:"), 0, 4);
                info.add(new Label(Integer.toString(bus.getLicenseNumber())), 0, 5);
                info.add(new Label("Plate No.:"), 0, 6);
                info.add(new Label(bus.getPlateNumber()), 0, 7);
                info.add(new Label("Nationality:"), 1, 0);
                info.add(new Label(d.getNationality()), 1, 1);
                info.add(new Label("Mobile No.:"), 1, 2);
                info.add(new Label(d.getPhone_number()), 1, 3);
                info.add(new Label("Fee"), 1, 4);
                info.add(new Label(Double.toString(d.getFee())), 1, 5);
                info.add(new Label("Served City:"), 1, 6);
                info.add(new Label(d.getServedCity()), 1, 7);
                String tempString = "";
                for (String driverNeighborhood : driverNeighborhoods) {
                    tempString += " " + driverNeighborhood;
                }
                info.add(new Label("Served neighborhhods: "), 0, 8);
                info.add(new Label(tempString), 1, 8);

                VBox driverVBox = new VBox();
                driverVBox.getStyleClass().add("sec");
                driverVBox.setPadding(new Insets(10, 10, 10, 10));
                driverVBox.getChildren().add(top);
                driverVBox.getChildren().add(info);
                driverVBox.setMaxWidth(295);
                //driverVbox.setMargin(top,new Insets(0,0,2,5));
                driverVBox.setMargin(info, new Insets(0, 0, 7, 5));

                driverVBox.setOnMouseClicked(e -> {
                    totime = "";
                    fromtime = "";

                    Session session2 = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction2 = session2.beginTransaction();
                    List<TripsSchedule_toCampus> tolist2 = new ArrayList<TripsSchedule_toCampus>();
                    Query querySelect2 = session2.createQuery("from TripsSchedule_toCampus where busId=" + driver.getBusId());
                    tolist2 = querySelect2.list();

                    System.out.println("list size: " + tolist2.size());
                    for (TripsSchedule_toCampus t : tolist2) {
                        System.out.println(t.getBusId() + " - " + t.getToCampusTime());
                        tolist3.add(t.getToCampusTime());
                    }

                    List<TripsSchedule_fromCampus> fromlist2 = new ArrayList<TripsSchedule_fromCampus>();
                    querySelect2 = session2.createQuery("from TripsSchedule_fromCampus where busId=" + driver.getBusId());
                    fromlist2 = querySelect2.list();
                    transaction2.commit();
                    session2.close();

                    System.out.println("list size: " + fromlist2.size());
                    for (TripsSchedule_fromCampus t : fromlist2) {
                        System.out.println(t.getBusId() + " - " + t.getFromCampusTime());
                        fromlist3.add(t.getFromCampusTime());
                    }

                    System.out.println("HERE-----------------");
                    for (String t : fromlist3) {
                        System.out.println(t);
                        if (fromlist3.get(0).equals(t)) {
                            fromtime = fromtime.concat(t);
                        } else {
                            fromtime = fromtime.concat(", " + t);
                        }
                    }
                    System.out.println(totime);
                    for (String t : tolist3) {
                        System.out.println(t);
                        if (tolist3.get(0).equals(t)) {
                            totime = totime.concat(t);
                        } else {
                            totime = totime.concat(", " + t);
                        }
                    }
                    System.out.println(fromtime);

                    Alert studentChoice = new Alert(Alert.AlertType.CONFIRMATION);
                    studentChoice.setTitle("");
                    studentChoice.setHeaderText(null);
                    studentChoice.setContentText("You chose " + driver.getName()
                            + "\n\nThe Bus Schedule is: \n"
                            + "\nFrom Campus: " + fromtime
                            + "\nTo Campus: " + totime
                            + "\n\nDo you want to continue?\n");
                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    studentChoice.getButtonTypes().setAll(yesButton, cancelButton);
                    Optional<ButtonType> result = studentChoice.showAndWait();

                    if (result.get() == yesButton) {
                        CreateStudentAccountController.student.setDriverId(d.getDriverId());

                        Session session1 = HibernateUtil.getSessionFactory().openSession();
                        Transaction transaction1 = session1.beginTransaction();
                        session1.save(CreateStudentAccountController.studentAddress);
                        CreateStudentAccountController.student.setZipcode(CreateStudentAccountController.studentAddress.getZipcode());
                        session1.save(CreateStudentAccountController.student);

                        System.out.println(CreateStudentAccountController.student.getStudentId());
                        for (Schedule_of_student schedule : StudentScheduleController.schedule_of_student) {
                            schedule.setStudentId(CreateStudentAccountController.student.getStudentId());
                            session1.save(schedule);
                        }
                        transaction1.commit();
                        session1.close();

                        Alert redirect = new Alert(Alert.AlertType.CONFIRMATION);
                        redirect.setTitle("");
                        redirect.setHeaderText(null);
                        redirect.setContentText("Done! you can now log in to use the system");
                        redirect.showAndWait();
                        try {
                            root = FXMLLoader.load(getClass().getResource("login.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(AvailableBusesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                });
                i += 1;
                containerBuses.getChildren().add(driverVBox);
            }
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("createStudentAccount.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goForward(ActionEvent event) throws IOException {

//        root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

}
