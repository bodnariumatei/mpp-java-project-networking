package rpcprotocol;

import dto.DTOUtils;
import dto.OperatorDTO;
import sm.model.Operator;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RpcServicesProxy implements ISwimMasterServices {
    private String host;
    private int port;

    private ISwimMasterObserver client;

    private ObjectInputStream input;
        private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public RpcServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public void login(Operator operator) throws SwimMasterException {
        initializeConnection();
        OperatorDTO opDTO= DTOUtils.getDTO(operator);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(opDTO).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new SwimMasterException(err);
        }
    }

    private void sendRequest(Request request) throws SwimMasterException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new SwimMasterException("Error sending object "+ e);
        }
    }

    private Response readResponse() {
        Response response=null;
        try{
            response=qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try{
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
    private boolean isUpdate(Response response){
        return response.type()== ResponseType.PARTICIPANT_REGISTERED;
    }
    private void handleUpdate(Response response) {
    }
}
