package dto;

import sm.model.Competition;
import sm.model.CompetitionStyle;
import sm.model.Operator;
import sm.model.Participant;
import sm.model.utils.CompetitionItem;
import sm.model.utils.ParticipantItem;

import java.time.LocalDateTime;

public class DTOUtils {
    public static Operator getFromDTO(OperatorDTO opdto){
        String username = opdto.getUsername();
        String password = opdto.getPassword();
        return new Operator(username, password);
    }

    public static OperatorDTO getDTO(Operator op){
        String username = op.getUsername();
        String password = op.getPassword();
        return new OperatorDTO(username, password);
    }

    public static CompetitionItem getFromDTO(CompetitionItemDTO competition){
        int id = competition.getId();
        CompetitionStyle style = competition.getStyle();
        int distance = competition.getDistance();
        int noParticipants = competition.getNoParticipants();
        CompetitionItem ci = new CompetitionItem(distance, style, noParticipants);
        ci.setId(id);
        return ci;
    }

    public static CompetitionItemDTO getDTO(CompetitionItem competition){
        int id = competition.getId();
        CompetitionStyle style = competition.getStyle();
        int distance = competition.getDistance();
        int noParticipants = competition.getNoParticipants();
        return new CompetitionItemDTO(id, style, distance, noParticipants);
    }

    public static ParticipantItem getFromDTO(ParticipantItemDTO participant){
        String name = participant.getName();
        int age = participant.getAge();
        String competitions = participant.getCompetitions();
        return new ParticipantItem(name, age, competitions);
    }

    public static ParticipantItemDTO getDTO(ParticipantItem participant){
        String name = participant.getName();
        int age = participant.getAge();
        String competitions = participant.getCompetitions();
        return new ParticipantItemDTO(name, age, competitions);
    }

    public static Participant getFromDTO(ParticipantDTO participant){
        String name = participant.getName();
        LocalDateTime dateOfBirth = participant.getDateOfBirth();
        return new Participant(name, dateOfBirth);
    }

    public static ParticipantDTO getDTO(Participant participant){
        String name = participant.getName();
        LocalDateTime dateOfBirth = participant.getDateOfBirth();
        return new ParticipantDTO(name, dateOfBirth);
    }

    public static ParticipantDTO getDTO(Participant participant, Iterable<CompetitionItemDTO> competitions){
        String name = participant.getName();
        LocalDateTime dateOfBirth = participant.getDateOfBirth();
        return new ParticipantDTO(name, dateOfBirth, competitions);
    }
}
