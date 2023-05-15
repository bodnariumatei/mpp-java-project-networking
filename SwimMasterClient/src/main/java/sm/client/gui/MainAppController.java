package sm.client.gui;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sm.model.Operator;
import sm.model.Participant;
import sm.model.utils.CompetitionItem;
import sm.model.utils.ParticipantItem;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MainAppController implements ISwimMasterObserver {
    private ISwimMasterServices server;
    private Operator currentOperator;

    public void setServer(ISwimMasterServices server) {
        this.server = server;
    }
    public void setCurrentOperator(Operator op) {
        this.currentOperator = op;
    }

    @FXML
    private TableView<CompetitionItem> competitionsTableView;
    private TableView.TableViewSelectionModel<CompetitionItem> selectionModel;
    @FXML
    private TableColumn<CompetitionItem, String> styleColumn;
    @FXML
    private TableColumn<CompetitionItem, String> distanceColumn;
    @FXML
    private TableColumn<CompetitionItem, String> noParticipantsColumn;

    @FXML
    private TableView<ParticipantItem> participantsTableView;
    @FXML
    private TableColumn<ParticipantItem, String> nameColumn;
    @FXML
    private TableColumn<ParticipantItem, String> ageColumn;
    @FXML
    private TableColumn<ParticipantItem, String> competitionsColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private ListView<CompetitionItem> competitionsListView;

    private CompetitionItem currentCti;

    @FXML
    public void logout(ActionEvent event){
        try {
            server.logout(currentOperator);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login_scene.fxml"));
            Parent root = fxmlLoader.load();

            LoginSceneController ctrl = fxmlLoader.getController();
            ctrl.setServer(server);

            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (SwimMasterException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't log out!");
            alert.show();
            System.out.println(e.getMessage());
        }
    }

    public void loadScene() {
        setUpCompetitionsTable();
        loadCompetitionsTable();
        loadCompetitionsListView();
    }

    private void setUpCompetitionsTable(){
        competitionsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        styleColumn.setCellValueFactory(new PropertyValueFactory<>("style"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        noParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("noParticipants"));

        selectionModel = competitionsTableView.getSelectionModel();

        ObservableList<CompetitionItem> selectedItems = selectionModel.getSelectedItems();

        selectedItems.addListener(
                (ListChangeListener<? super CompetitionItem>) change -> {
                    currentCti = selectionModel.getSelectedItem();
                    loadParticipantsTable();
                });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        competitionsColumn.setCellValueFactory(new PropertyValueFactory<>("competitions"));
    }

    private void loadCompetitionsTable(){
        competitionsTableView.getItems().clear();
        Iterable<CompetitionItem> tableItems = null;
        try {
            tableItems = server.getCompetitions();
            for(CompetitionItem cti : tableItems){
                competitionsTableView.getItems().add(cti);
            }
        } catch (SwimMasterException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadParticipantsTable() {
        CompetitionItem cti = currentCti;
        if(cti != null) {
            try {
                participantsTableView.getItems().clear();
                CompetitionItem ci = new CompetitionItem(cti.getDistance(), cti.getStyle());
                ci.setId(cti.getId());
                Iterable<ParticipantItem> tableItems = server.getParticipants(ci);
                for(ParticipantItem pti : tableItems){
                    participantsTableView.getItems().add(pti);
                }
            } catch (SwimMasterException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void loadCompetitionsListView(){
        competitionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            Iterable<CompetitionItem> competitions = server.getCompetitions();
            for(CompetitionItem c: competitions){
                competitionsListView.getItems().add(c);
            }
        } catch (SwimMasterException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void register(){
       if(nameTextField.getText().isEmpty()){
            System.out.println("Nume invalid");
            return;
        }
        if(dateOfBirthDatePicker.getValue() == null){
            System.out.println("Data nu e oke");
            return;
        }
        List<CompetitionItem> competitions = competitionsListView.getSelectionModel().getSelectedItems();
        if(competitions.size() == 0){
            System.out.println("N-ai selectat nimic");
            return;
        }

        String name = nameTextField.getText();
        LocalDateTime dateOfBirth = dateOfBirthDatePicker.getValue().atStartOfDay();

        try {
            server.register(new Participant(name, dateOfBirth), competitions);
        } catch (SwimMasterException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void participantRegistered() throws SwimMasterException {
        Platform.runLater(() -> {
            loadParticipantsTable();
            loadCompetitionsTable();
        });
    }
}
