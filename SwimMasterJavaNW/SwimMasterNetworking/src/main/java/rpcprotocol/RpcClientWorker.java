package rpcprotocol;

import dto.*;
import sm.model.Competition;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                Object request = input.readObject();
                Response response = handleRequest((Request)request);
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
                server.login(operator, this);
                return okResponse;
            } catch (SwimMasterException e) {
                connected=false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()== RequestType.LOGOUT){
            System.out.println("Logout request ..."+request.type());
            OperatorDTO opDTO=(OperatorDTO) request.data();
            Operator operator= DTOUtils.getFromDTO(opDTO);
            try {
                server.logout(operator);
                connected=false;
                return okResponse;
            } catch (SwimMasterException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_COMPETITIONS){
            System.out.println("Get competitions request ..."+request.type());
            try{
                Iterable<CompetitionItem> competitions = server.getCompetitions();
                Set<CompetitionItemDTO> competitionItemDTOS = new HashSet<>();
                for(CompetitionItem c: competitions){
                    competitionItemDTOS.add(DTOUtils.getDTO(c));
                }
                return new Response.Builder().type(ResponseType.COMPETITIONS_DELIVERED).data(competitionItemDTOS).build();
            } catch (SwimMasterException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_PARTICIPANTS){
            System.out.println("Get participants request ..."+request.type());
            try{
                CompetitionItemDTO competitionDTO = (CompetitionItemDTO) request.data();
                CompetitionItem competition = DTOUtils.getFromDTO(competitionDTO);
                Iterable<ParticipantItem> participantItems = server.getParticipants(competition);
                Set<ParticipantItemDTO> participantItemDTOS = new HashSet<>();
                for(ParticipantItem p: participantItems){
                    participantItemDTOS.add(DTOUtils.getDTO(p));
                }
                return new Response.Builder().type(ResponseType.PARTICIPANTS_DELIVERED).data(participantItemDTOS).build();
            } catch (SwimMasterException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.ADD_PARTICIPANT){
            System.out.println("Add participant request ..."+request.type());
            try{
                ParticipantDTO participantDTO = (ParticipantDTO) request.data();
                Participant participant = DTOUtils.getFromDTO(participantDTO);
                server.addParticipant(participant);
                return okResponse;
            } catch (SwimMasterException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.REGISTER_PARTICIPANT){
            System.out.println("Register participant request ..."+request.type());
            try{
                ParticipantDTO participantDTO = (ParticipantDTO) request.data();
                Participant participant = DTOUtils.getFromDTO(participantDTO);
                Iterable<CompetitionItemDTO> competitionsDTOS = participantDTO.getCompetitions();
                List<CompetitionItem> competitions = new ArrayList<>();
                for(CompetitionItemDTO c:competitionsDTOS){
                    competitions.add(DTOUtils.getFromDTO(c));
                }
                server.register(participant, competitions);
                return okResponse;
            } catch (SwimMasterException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        return null;
    }

    @Override
    public void participantRegistered() {
        Response resp=new Response.Builder().type(ResponseType.PARTICIPANT_REGISTERED).build();
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
