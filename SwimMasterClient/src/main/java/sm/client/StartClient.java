package sm.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rpcprotocol.RpcServicesProxy;
import sm.client.gui.LoginSceneController;
import sm.client.gui.MainAppController;
import sm.services.ISwimMasterServices;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {
    private static int defaultChatPort = 44444;
    private static String defaultServer = "localhost";


    public void start(Stage stage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("smserver.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find smserver.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("sm.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("sm.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ISwimMasterServices server = new RpcServicesProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("gui/login_scene.fxml"));
        Scene scene= new Scene(loader.load());

        LoginSceneController ctrl = loader.getController();
        ctrl.setServer(server);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
