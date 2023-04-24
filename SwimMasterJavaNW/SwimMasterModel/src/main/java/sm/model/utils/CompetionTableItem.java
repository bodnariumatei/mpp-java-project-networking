package sm.model.utils;


import sm.model.Competition;
import sm.model.CompetitionStyle;

public class CompetionTableItem extends Competition {
    private int noParticipants;

    public CompetionTableItem(int distance, CompetitionStyle style, int noParticipants) {
        super(distance, style);
        this.noParticipants = noParticipants;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }
}
