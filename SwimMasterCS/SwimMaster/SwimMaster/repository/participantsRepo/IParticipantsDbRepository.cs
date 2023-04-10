using SwimMaster.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimMaster.repository.participantsRepo
{
    internal interface IParticipantsDbRepository:IRepository<int, Participant>
    {
        public IEnumerable<Participant> getAllFromCompetition(int competitionId);

        public Participant getOneByName(string name);
    }
}
