package lk.ijse.CarHire.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.util.SessionFactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DashboardFormController {
    public AnchorPane root;
    public AnchorPane root1;
    public Label lblTime;
    public Label lblDate;
    public Label totalCustomersLabel;
    public Label totalCarsLabel;
    public Label totalRentsLabel;
    private volatile boolean stop = false;

    public void initialize(){
        dt();
        Timenow();
        TotalCustomers();
        TotalCars();
        TotalRents();
    }
    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard | CarHire");
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        this.root1.getChildren().clear();
        this.root1.getChildren().add(root1);
        stop = true;
    }

    public void btnCarsOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/car_form.fxml"));
        this.root1.getChildren().clear();
        this.root1.getChildren().add(root1);
        stop = true;
    }

    public void btnCategoriesOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/car_category_form.fxml"));
        this.root1.getChildren().clear();
        this.root1.getChildren().add(root1);
        stop = true;
    }

    public void btnRentsOnAction(ActionEvent actionEvent) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/rent_form.fxml"));
        this.root1.getChildren().clear();
        this.root1.getChildren().add(root1);
        stop = true;
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to Log Out ?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    LogOut();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void LogOut() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/signin_form.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("SignIn | CarHire");
        stop = true;
    }

    public void dt(){
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dd = simpleDateFormat.format(d);
        lblDate.setText(dd);
    }

    private void Timenow(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000);
                } catch (Exception e){
                    System.out.println(e);
                }
                String timenow = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    public void TotalCustomers(){
        int totalCustomers = getTotalCustomersFromDatabase();
        totalCustomersLabel.setText("Total Customers: " + totalCustomers);
    }

    public void TotalCars(){
        int totalCars = getTotalCarsFromDatabase();
        totalCarsLabel.setText("Total Cars: " + totalCars);
    }

    public void TotalRents(){
        int totalRents = getTotalRentsFromDatabase();
        totalRentsLabel.setText("Total Rents: " + totalRents);
    }

    private int getTotalRentsFromDatabase(){
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(c) FROM RentEntity c", Long.class);
            Long totalRents = query.getSingleResult();
            return totalRents.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getTotalCarsFromDatabase(){
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(c) FROM CarEntity c", Long.class);
            Long totalCars = query.getSingleResult();
            return totalCars.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getTotalCustomersFromDatabase() {
        try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(c) FROM CustomerEntity c", Long.class);
            Long totalCustomers = query.getSingleResult();
            return totalCustomers.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}