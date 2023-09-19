package lk.ijse.CarHire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.CarHire.dao.custom.impl.UserDaoImpl;

import java.io.IOException;

public class SigninFormController {

    public AnchorPane rootNode;
    public TextField txtusername;
    public TextField txtpassword;
    public void btnSigninOnAction(ActionEvent actionEvent) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();
        try {
            UserDaoImpl userDao = new UserDaoImpl();

            // Check if a user with the given username and password exists
            if (userDao.valid(username, password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "SignedIn Successful!");
                alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                       Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
                        Scene scene = new Scene(rootNode);
                        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
                        primaryStage.setScene(scene);
                        primaryStage.centerOnScreen();
                        primaryStage.setTitle("Dashboard | CarHire");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            } else {
                // Invalid credentials, show an error message
                new Alert(Alert.AlertType.ERROR, "Invalid username or password").showAndWait();
            }
        } catch (Exception e) {
            // Handle exceptions if any
            e.printStackTrace();
        }
    }

    public void btnRegisterOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("SignUp | CarHire");
    }
}