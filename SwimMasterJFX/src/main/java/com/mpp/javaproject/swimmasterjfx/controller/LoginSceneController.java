package com.mpp.javaproject.swimmasterjfx.controller;

import com.mpp.javaproject.swimmasterjfx.domain.Operator;
import com.mpp.javaproject.swimmasterjfx.repository.operator.OperatorsDbRepository;
import com.mpp.javaproject.swimmasterjfx.service.OperatorsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginSceneController {
    private OperatorsService srv;
    @FXML
    public void initialize() {
        Properties props=new Properties();
        try {
            props.load(new FileReader("db.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find db.properties "+e);
        }
        OperatorsDbRepository opRepo=new OperatorsDbRepository(props);
        this.srv = new OperatorsService(opRepo);
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void login(ActionEvent event) throws IOException {
        String usernameString = usernameTextField.getText();
        String passwordString = passwordField.getText();
        if (usernameString.equals("") || passwordString.equals("")){
            errorLabel.setText("Username and Password can't be empty!");
            return;
        }

        Operator operator = srv.getOperatorByUsername(usernameString);
        if(operator == null){
            errorLabel.setText("Wrong username or password!");
            return;
        }
        if(!operator.getPassword().equals(passwordString)){
            errorLabel.setText("Wrong username or password!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_scene.fxml"));
        Scene mainScene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(true);
        stage.setMinHeight(510);
        stage.setMinWidth(550);
        stage.setTitle("SwimMaster");
        stage.setScene(mainScene);
    }

}