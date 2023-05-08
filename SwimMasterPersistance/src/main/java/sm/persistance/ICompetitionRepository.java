package sm.persistance;


import sm.model.Competition;
import sm.model.utils.CompetitionItem;

public interface ICompetitionRepository extends IRepository<Integer, Competition> {
    public Iterable<CompetitionItem> getAllWithNrOfParticipants();

    public Iterable<Competition> getCompetitionsForParticipant(int participant_id);

    public void registerAtCompetition(int participant_id, int competition_id);
}
