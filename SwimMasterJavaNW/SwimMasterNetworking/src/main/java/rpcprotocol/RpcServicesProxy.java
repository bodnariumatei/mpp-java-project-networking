package rpcprotocol;

import dto.*;
import sm.model.Operator;
import sm.model.Participant;
import sm.model.utils.CompetitionItem;
import sm.model.utils.ParticipantItem;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public void login(Operator operator, ISwimMasterObserver client) throws SwimMasterException {
        initializeConnection();
        OperatorDTO opDTO= DTOUtils.getDTO(operator);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(opDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.OK){
            this.client = client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new SwimMasterException(err);
        }
    }

    @Override
    public void logout(Operator operator) throws SwimMasterException {
        OperatorDTO operatorDTO= DTOUtils.getDTO(operator);
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(operatorDTO).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new SwimMasterException(err);
        }
    }

    @Override
    public Iterable<CompetitionItem> getCompetitions() throws SwimMasterException {
       Request req = new Request.Builder().type(RequestType.GET_COMPETITIONS).build();
       sendRequest(req);
       Response response = readResponse();
       if(response.type() == ResponseType.COMPETITIONS_DELIVERED){
           Iterable<CompetitionItemDTO> competitionsDTOS = (Iterable<CompetitionItemDTO>) response.data();
           Set<CompetitionItem> competitions = new HashSet<>();
           for(CompetitionItemDTO cdto: competitionsDTOS){
               competitions.add(DTOUtils.getFromDTO(cdto));
           }
           return competitions;
       }
       if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new SwimMasterException(err);
       }
       return null;
    }

    @Override
    public Iterable<ParticipantItem> getParticipants(CompetitionItem competition) throws SwimMasterException {
        Request req = new Request.Builder().type(RequestType.GET_PARTICIPANTS).data(DTOUtils.getDTO(competition)).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.PARTICIPANTS_DELIVERED){
            Iterable<ParticipantItemDTO> participantsDTOS = (Iterable<ParticipantItemDTO>) response.data();
            Set<ParticipantItem> participants = new HashSet<>();
            for(ParticipantItemDTO pdto: participantsDTOS){
                participants.add(DTOUtils.getFromDTO(pdto));
            }
            return participants;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new SwimMasterException(err);
        }
        return null;
    }

    @Override
    public Participant addParticipant(Participant participant) throws SwimMasterException {
        Request req = new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(DTOUtils.getDTO(participant)).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new SwimMasterException(err);
        }
        return null;
    }

    @Override
    public void register(Participant participant, List<CompetitionItem> competitions) throws SwimMasterException {
        Set<CompetitionItemDTO> competitionDTOS = new HashSet<>();
        for(CompetitionItem ci : competitions)
            competitionDTOS.add(DTOUtils.getDTO(ci));
        Request req = new Request.Builder().type(RequestType.REGISTER_PARTICIPANT).data(DTOUtils.getDTO(participant, competitionDTOS)).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
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
        if (response.type()== ResponseType.PARTICIPANT_REGISTERED){
            try {
                client.participantRegistered();
            } catch (SwimMasterException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
