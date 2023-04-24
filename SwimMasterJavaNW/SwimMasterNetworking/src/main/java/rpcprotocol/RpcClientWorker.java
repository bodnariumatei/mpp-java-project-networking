package rpcprotocol;

import dto.DTOUtils;
import dto.OperatorDTO;
import sm.model.Competition;
import sm.model.Operator;
import sm.model.Participant;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClientWorker implements Runnable, ISwimMasterObserver {
    private ISwimMasterServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public RpcClientWorker(ISwimMasterServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
    private Response handleRequest(Request request) {
        if (request.type()== RequestType.LOGIN){
            System.out.println("Login request ..."+request.type());
            OperatorDTO opDTO=(OperatorDTO) request.data();
            Operator operator= DTOUtils.getFromDTO(opDTO);
            try {
                server.login(operator);
                return okResponse;
            } catch (SwimMasterException e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return null;
    }

    @Override
    public void participantRegistered(Participant participant, Iterable<Competition> competitions) {

    }
}
