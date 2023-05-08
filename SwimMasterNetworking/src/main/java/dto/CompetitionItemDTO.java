package dto;

import sm.model.CompetitionStyle;
import sm.model.utils.CompetitionItem;

import java.io.Serializable;

public class CompetitionItemDTO implements Serializable {
    private int id;
    private CompetitionStyle style;
    private int distance, noParticipants;

    public CompetitionItemDTO(int id, CompetitionStyle style, int distance, int noParticipants) {
        this.id = id;
        this.style = style;
        this.distance = distance;
        this.noParticipants = noParticipants;
    }

    public CompetitionItemDTO(int id, CompetitionStyle style, int distance) {
        this.id = id;
        this.style = style;
        this.distance = distance;
        this.noParticipants = -1;
    }

    public CompetitionStyle getStyle() {
        return style;
    }

    public void setStyle(CompetitionStyle style) {
        this.style = style;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
