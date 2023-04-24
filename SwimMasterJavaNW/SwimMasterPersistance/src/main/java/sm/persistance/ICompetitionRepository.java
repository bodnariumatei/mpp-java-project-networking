package sm.persistance;


import sm.model.Competition;
import sm.model.utils.CompetionTableItem;

public interface ICompetitionRepository extends IRepository<Integer, Competition> {
    public Iterable<CompetionTableItem> getAllWithNrOfParticipants();

    public Iterable<Competition> getCompetitionsForParticipant(int participant_id);

    public void registerAtCompetition(int participant_id, int competition_id);
}
