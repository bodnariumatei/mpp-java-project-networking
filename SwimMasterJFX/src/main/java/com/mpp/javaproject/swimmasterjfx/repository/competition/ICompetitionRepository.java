package com.mpp.javaproject.swimmasterjfx.repository.competition;

import com.mpp.javaproject.swimmasterjfx.domain.Competition;
import com.mpp.javaproject.swimmasterjfx.repository.IRepository;
import com.mpp.javaproject.swimmasterjfx.utils.tableview_items.CompetionTableItem;

public interface ICompetitionRepository extends IRepository<Integer, Competition> {


    public Iterable<CompetionTableItem> getAllWithNrOfParticipants();

    public Iterable<Competition> getCompetitionsForParticipant(int participant_id);

    public void registerAtCompetition(int participant_id, int competition_id);
}
