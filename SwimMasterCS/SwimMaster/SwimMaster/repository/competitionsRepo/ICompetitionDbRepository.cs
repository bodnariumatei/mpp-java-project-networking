using SwimMaster.domain;

namespace SwimMaster.repository.competitionsRepo
{
    internal interface ICompetitionDbRepository:IRepository<int, Competition>{
        public IEnumerable<(Competition, int)> GetAllWithNoParticipants();

        public IEnumerable<Competition> GetAllForParticipant(int participantId);

        public void registerParticipantAtCompetition(int participantId, int competitionId);
    }
}
