package sm.server;

import sm.model.Competition;
import sm.model.Operator;
import sm.model.Participant;
import sm.model.utils.CompetitionItem;
import sm.model.utils.ParticipantItem;
import sm.persistance.IOperatorsRepository;
import sm.persistance.repository.CompetitionDbRepository;
import sm.persistance.repository.OperatorsDbRepository;
import sm.persistance.repository.ParticipantDbRepository;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImpl implements ISwimMasterServices {
    IOperatorsRepository opRepository;
    CompetitionDbRepository compeRepository;
    ParticipantDbRepository participantRepo;
    private Map<Integer, ISwimMasterObserver> loggedClients;

    public ServicesImpl(IOperatorsRepository operatorsRepository,
                        CompetitionDbRepository competitionsRepository,
                        ParticipantDbRepository participantRepo){
        this.opRepository = operatorsRepository;
        this.compeRepository = competitionsRepository;
        this.participantRepo = participantRepo;
        loggedClients = new HashMap<>();
    }

    @Override
    public synchronized void login(Operator operator, ISwimMasterObserver client) throws SwimMasterException {
        Operator op = opRepository.getOneByUsernameAndPassword(operator.getUsername(), operator.getPassword());
        if (op!=null){
            if(loggedClients.containsKey(op.getId()))
                throw new SwimMasterException("User already logged in.");
            loggedClients.put(op.getId(), client);
        }else
            throw new SwimMasterException("Authentication failed.");
    }

    @Override
    public synchronized void logout(Operator operator) throws SwimMasterException {
        Operator op = opRepository.getOneByUsernameAndPassword(operator.getUsername(), operator.getPassword());
        boolean checker = loggedClients.containsKey(op.getId());
        if (!checker)
            throw new SwimMasterException("Operator "+operator.getUsername()+" is not logged in.");
        loggedClients.remove(op.getId());
    }

    @Override
    public synchronized Iterable<CompetitionItem> getCompetitions() throws SwimMasterException {

        Iterable<CompetitionItem> competitions=compeRepository.getAllWithNrOfParticipants();
        return competitions;
    }

    @Override
    public Iterable<ParticipantItem> getParticipants(CompetitionItem competition) {
        List<ParticipantItem> participantTableItems = new ArrayList<>();
        Competition compe = compeRepository.getOneByStyleAndDistance(competition.getStyle(), competition.getDistance());
        Iterable<Participant> participants = participantRepo.getAllFromCompetition(compe.getId());

        for(Participant p: participants){
            StringBuilder competitionsString = new StringBuilder();
            Iterable<Competition> competitions = compeRepository.getCompetitionsForParticipant(p.getId());
            for(Competition c:competitions){
                competitionsString.append(c.getStyle()).append(" - ").append(c.getDistance()).append("m");
                competitionsString.append("\n");
            }
            participantTableItems.add(new ParticipantItem(p.getName(), p.getAge(), competitionsString.toString()));
        }
        return participantTableItems;
    }

    @Override
    public Participant addParticipant(Participant participant) throws SwimMasterException {
        if(participantRepo.getOneByName(participant.getName()) == null){
            participantRepo.store(participant);
        }
        return participantRepo.getOneByName(participant.getName());
    }

    @Override
    public void register(Participant participant, List<CompetitionItem> competitions) throws SwimMasterException {
        Participant p = addParticipant(participant);
        for(CompetitionItem c: competitions){
            compeRepository.registerAtCompetition(p.getId(), c.getId());
        }
        notifyClients();
    }


    private final int defaultThreadsNo = 5;
    public void notifyClients(){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(ISwimMasterObserver c:loggedClients.values()){
            executor.execute(() -> {
                try {
                    c.participantRegistered();
                } catch (SwimMasterException e) {
                    System.err.println("Error notifying clients " + e);
                }
            });
        }
    }
}
