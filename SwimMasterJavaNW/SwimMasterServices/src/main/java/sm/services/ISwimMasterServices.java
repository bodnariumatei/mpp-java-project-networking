package sm.services;

import sm.model.Competition;
import sm.model.Operator;
import sm.model.Participant;
import sm.model.utils.CompetitionItem;
import sm.model.utils.ParticipantItem;

import java.time.LocalDateTime;
import java.util.List;

public interface ISwimMasterServices {
    // all actions made by the service in the previous version of the app
    void login(Operator operator, ISwimMasterObserver client) throws SwimMasterException;

    void logout(Operator operator) throws SwimMasterException;

    Iterable<CompetitionItem> getCompetitions() throws SwimMasterException;

    Iterable<ParticipantItem> getParticipants(CompetitionItem competition) throws SwimMasterException;

    Participant addParticipant(Participant participant) throws SwimMasterException;

    void register(Participant participant, List<CompetitionItem> competitions) throws SwimMasterException;
}
