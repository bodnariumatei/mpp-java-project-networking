package sm.model.utils;


import sm.model.Competition;
import sm.model.CompetitionStyle;

public class CompetitionItem extends Competition {
    private int noParticipants;

    public CompetitionItem(int distance, CompetitionStyle style, int noParticipants) {
        super(distance, style);
        this.noParticipants = noParticipants;
    }

    public CompetitionItem(int distance, CompetitionStyle style) {
        super(distance, style);
        this.noParticipants = -1;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }
}
