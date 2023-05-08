package dto;

import sm.model.Competition;
import sm.model.utils.CompetitionItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ParticipantDTO implements Serializable {
    String name;
    LocalDateTime dateOfBirth;
    Set<CompetitionItemDTO> competitions;

    public ParticipantDTO(String name, LocalDateTime dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public ParticipantDTO(String name, LocalDateTime dateOfBirth, Iterable<CompetitionItemDTO> competitions) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.competitions = new HashSet<>();
        for(CompetitionItemDTO c:competitions){
            this.competitions.add(c);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Iterable<CompetitionItemDTO> getCompetitions() {
        return competitions;
    }
}
