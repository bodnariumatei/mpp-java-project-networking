import sm.persistance.repository.CompetitionDbRepository;
import sm.persistance.repository.OperatorsDbRepository;
import sm.persistance.repository.ParticipantDbRepository;
import sm.server.ServicesImpl;
import sm.services.ISwimMasterServices;
import utils.AbstractServer;
import utils.RpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=44444;
    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/smserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find smserver.properties "+e);
            return;
        }
        OperatorsDbRepository opRepo = new OperatorsDbRepository(serverProps);
        CompetitionDbRepository compeRepo = new CompetitionDbRepository(serverProps);
        ParticipantDbRepository partRepo = new ParticipantDbRepository(serverProps);
        ISwimMasterServices chatServerImpl = new ServicesImpl(opRepo, compeRepo, partRepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("sm.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
