using log4net.Config;
using SwimMaster.domain;
using SwimMaster.repository.competitionsRepo;
using SwimMaster.repository.participantsRepo;
using System;
using System.Collections.Generic;
using System.Drawing.Printing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tasks.repository;

namespace SwimMaster.service
{
    internal class ContestService
    {
        private CompetitionDbRepository competitionDbRepository;
        private ParticipantDbRepository participantDbRepository;

        public ContestService() {
            XmlConfigurator.Configure(new FileInfo("app.config"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", DBUtils.GetConnectionStringByName("swimMasterDB"));

            this.competitionDbRepository = new CompetitionDbRepository(props);
            this.participantDbRepository = new ParticipantDbRepository(props);    
        }

        public IEnumerable<Competition> getCompetitions()
        {
            return competitionDbRepository.getAll();
        }

        public IEnumerable<(Competition, int)> getCompetitionsWithNoParticipants()
        {
            return competitionDbRepository.GetAllWithNoParticipants();
        }

        public IEnumerable<Participant> getParticipants() { 
            return participantDbRepository.getAll();
        }

        public IEnumerable<Participant> getParticipantsFromCompetition(int competitionId)
        {
            return participantDbRepository.getAllFromCompetition(competitionId);
        }

        public IEnumerable<Competition> GetCompetitionsForParticipant(int participantId)
        {
            return competitionDbRepository.GetAllForParticipant(participantId);
        }

        public void storeParticipant(string name, DateTime date)
        {
            Participant participant = participantDbRepository.getOneByName(name);
            if (participant == null)
            {
                participantDbRepository.store(new Participant(name, date));
            }
        }

        public void registerParticipantAtCompetition(string participantName,  int competitionId) {
            Participant participant = participantDbRepository.getOneByName(participantName);
            if (participant == null)
            {
                throw new Exception("Participantul nu exista!");
            }
            competitionDbRepository.registerParticipantAtCompetition(participant.Id, competitionId);
        }

        
    }
}
