package sm.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sm.model.Operator;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import javax.swing.*;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginSceneController {
    private ISwimMasterServices server;
    public void setServer(ISwimMasterServices server) {
        this.server = server;
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void login(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")){
            errorLabel.setText("Username and Password can't be empty!");
            return;
        }
        try {
            server.login(new Operator(username, password));
            changeScene(event);
        } catch (SwimMasterException e) {
            errorLabel.setText("Authentication error...");
            System.out.println(e.getMessage());
        }
    }

    private void changeScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_scene.fxml"));
            Scene mainScene = new Scene(loader.load());

            MainAppController ctrl = loader.getController();
            ctrl.setServer(server);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(true);
            stage.setMinHeight(510);
            stage.setMinWidth(550);
            stage.setTitle("SwimMaster");
            stage.setScene(mainScene);
        } catch (IOException e) {
            System.out.println("Can't load main_scene.fxml");
        }
    }

}