package sm.client.gui;

import dto.OperatorDTO;
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
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.io.IOException;

public class LoginSceneController {
    private MainAppController client;
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
    public void initialize(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_scene.fxml"));
        try {
            loader.load();
            this.client = loader.getController();
            client.setServer(server);
        } catch (IOException e) {
            System.out.println("Can't load main app client - " + e.getMessage());
        }

    }

    @FXML
    public void login(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")){
            errorLabel.setText("Username and Password can't be empty!");
            return;
        }
        try {
            Operator op = new Operator(username, password);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_scene.fxml"));
            Scene mainScene = new Scene(loader.load());
            this.client = loader.getController();
            client.setServer(server);

            server.login(op, client);

            client.setCurrentOperator(op);
            client.loadScene();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(true);
            stage.setMinHeight(510);
            stage.setMinWidth(550);
            stage.setTitle("SwimMaster");
            stage.setScene(mainScene);
        } catch (SwimMasterException e) {
            errorLabel.setText("Authentication error...");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Can't load main_scene.fxml");
        }
    }

}