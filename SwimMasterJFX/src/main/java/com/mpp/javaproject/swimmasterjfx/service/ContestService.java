package com.mpp.javaproject.swimmasterjfx.service;

import com.mpp.javaproject.swimmasterjfx.domain.Competition;
import com.mpp.javaproject.swimmasterjfx.domain.Participant;
import com.mpp.javaproject.swimmasterjfx.repository.competition.CompetitionDbRepository;
import com.mpp.javaproject.swimmasterjfx.repository.participant.ParticipantDbRepository;
import com.mpp.javaproject.swimmasterjfx.utils.tableview_items.CompetionTableItem;
import com.mpp.javaproject.swimmasterjfx.utils.tableview_items.ParticipantTableItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContestService {
    private ParticipantDbRepository pRepo;
    private CompetitionDbRepository cRepo;

    public ContestService(ParticipantDbRepository pRepo, CompetitionDbRepository cRepo) {
        this.pRepo = pRepo;
        this.cRepo = cRepo;
    }

    public Iterable<CompetionTableItem> getCompetitionTableItems() {
        return cRepo.getAllWithNrOfParticipants();
    }

    public Iterable<ParticipantTableItem> getParticipantsFromCompetition(int competition_id){
        List<ParticipantTableItem> participantTableItems = new ArrayList<>();

        Iterable<Participant> participants = pRepo.getAllFromCompetition(competition_id);

        for(Participant p: participants){
            StringBuilder competitionsString = new StringBuilder();
            Iterable<Competition> competitions = cRepo.getCompetitionsForParticipant(p.getId());
            for(Competition c:competitions){
                competitionsString.append(c.getStyle()).append(" - ").append(c.getDistance()).append("m");
                competitionsString.append("\n");
            }
            participantTableItems.add(new ParticipantTableItem(p.getName(), p.getAge(), competitionsString.toString()));
        }
        return participantTableItems;
    }

    public Iterable<Competition> getCompetitions() {
        return cRepo.getAll();
    }

    public void addParticipant(String name, LocalDateTime dateOfBirth){
        if(pRepo.getOneByName(name) == null){
            pRepo.store(new Participant(name, dateOfBirth));
        }
    }

    public void registerAtCompetitions(String name, List<Competition> competitions) {
        Participant p = pRepo.getOneByName(name);
        competitions.forEach(c -> {
            cRepo.registerAtCompetition(p.getId(), c.getId());
        });
    }
}
