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
import lk.ijse.CarHire.dto.UserDto;
import lk.ijse.CarHire.entity.UserEntity;
import lk.ijse.CarHire.service.ServiceFactory;
import lk.ijse.CarHire.service.custom.UserService;

import java.io.IOException;

public class SignupFormController {

    public AnchorPane rootNode;
    public TextField txtname;
    public TextField txtusername;
    public TextField txtemail;
    public TextField txtpassword;
    public TextField txtmobileno;

    UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceTypes.USER);

    public void btnSignupOnAction(ActionEvent actionEvent) throws Exception {

        try {
            String name = txtname.getText();
            String username = txtusername.getText();
            String email = txtemail.getText();
            String password = txtpassword.getText();
            int mobileno = Integer.parseInt(txtmobileno.getText());

            UserDto userDto = new UserDto();
            userDto.setName(name);
            userDto.setEmail(email);
            userDto.setMobileno(mobileno);
            userDto.setUsername(username);
            userDto.setPassword(password);

            userService.saveUser(userDto);
            new Alert(Alert.AlertType.INFORMATION, "SignedUp Successfully!").show();
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signin_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("SignIn | CarHire");

        } catch(Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validity() {
        return !txtname.getText().isEmpty() &&
                !txtemail.getText().isEmpty() &&
                !txtmobileno.getText().isEmpty() &&
                !txtpassword.getText().isEmpty() &&
                !txtusername.getText().isEmpty();
    }

    public void btnLoginOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signin_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("SignIn | CarHire");
    }
}