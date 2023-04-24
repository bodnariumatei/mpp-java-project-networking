package sm.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sm.model.Competition;
import sm.model.utils.CompetionTableItem;
import sm.model.utils.ParticipantTableItem;
import sm.services.ISwimMasterServices;

import java.io.IOException;

public class MainAppController {
    private ISwimMasterServices server;
    public void setServer(ISwimMasterServices server) {
        this.server = server;
    }

    @FXML
    private TableView<CompetionTableItem> competitionsTableView;
    private TableView.TableViewSelectionModel<CompetionTableItem> selectionModel;
    @FXML
    private TableColumn<CompetionTableItem, String> styleColumn;
    @FXML
    private TableColumn<CompetionTableItem, String> distanceColumn;
    @FXML
    private TableColumn<CompetionTableItem, String> noParticipantsColumn;

    @FXML
    private TableView<ParticipantTableItem> participantsTableView;
    @FXML
    private TableColumn<ParticipantTableItem, String> nameColumn;
    @FXML
    private TableColumn<ParticipantTableItem, String> ageColumn;
    @FXML
    private TableColumn<ParticipantTableItem, String> competitionsColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private ListView<Competition> competitionsListView;


    /*@FXML
    public void initialize() {
        Properties props=new Properties();
        try {
            props.load(new FileReader("db.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find db.properties "+e);
        }
        ParticipantDbRepository pRepo = new ParticipantDbRepository(props);
        CompetitionDbRepository cRepo = new CompetitionDbRepository(props);
        this.srv = new ContestService(pRepo, cRepo);

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

        ObservableList<CompetionTableItem> selectedItems = selectionModel.getSelectedItems();

        selectedItems.addListener(
                (ListChangeListener<? super CompetionTableItem>) change -> {
                    loadParticipantsTable();
                });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        competitionsColumn.setCellValueFactory(new PropertyValueFactory<>("competitions"));
    }

    private void loadCompetitionsTable(){
        competitionsTableView.getItems().clear();
        Iterable<CompetionTableItem> tableItems = srv.getCompetitionTableItems();
        for(CompetionTableItem cti : tableItems){
            competitionsTableView.getItems().add(cti);
        }
    }

    private void loadParticipantsTable() {
        CompetionTableItem cti = selectionModel.getSelectedItem();
        if(cti != null) {
            participantsTableView.getItems().clear();
            Iterable<ParticipantTableItem> tableItems = srv.getParticipantsFromCompetition(cti.getId());
            for(ParticipantTableItem pti : tableItems){
                participantsTableView.getItems().add(pti);
            }
        }
    }

    private void loadCompetitionsListView(){
        competitionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Iterable<Competition> competitions = srv.getCompetitions();
        for(Competition c: competitions){
            competitionsListView.getItems().add(c);
        }
    }*/


    @FXML
    public void register(){
       /* if(nameTextField.getText().isEmpty()){
            System.out.println("Nume invalid");
            return;
        }
        if(dateOfBirthDatePicker.getValue() == null){
            System.out.println("Data nu e oke");
            return;
        }
        List<Competition> competitions = competitionsListView.getSelectionModel().getSelectedItems();
        if(competitions.size() == 0){
            System.out.println("N-ai selectat nimic");
            return;
        }

        String name = nameTextField.getText();
        LocalDateTime dateOfBirth = dateOfBirthDatePicker.getValue().atStartOfDay();

        srv.addParticipant(name,dateOfBirth);
        srv.registerAtCompetitions(name, competitions);

        loadCompetitionsTable();
        loadParticipantsTable();*/
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login_scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}
