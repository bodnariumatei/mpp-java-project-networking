using log4net;
using Microsoft.VisualBasic.Logging;
using SwimMaster.domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tasks.repository;

namespace SwimMaster.repository.participantsRepo
{
    internal class ParticipantDbRepository : IParticipantsDbRepository
    {
        private static readonly ILog log = LogManager.GetLogger("OperatorDbRepository");
        IDictionary<string, string> props;

        public ParticipantDbRepository(IDictionary<string, string> props)
        {
            log.Info("Creating CompetitionDbRepository ");
            this.props = props;
        }

        public Participant getOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, name, date_of_birth from participants where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        string dateOfBirthString = dataR.GetString(2);
                        DateTime dateOfBirth = DateTime.Parse(dateOfBirthString);
                        Participant participant = new Participant(name, dateOfBirth); participant.Id = pid;

                        log.InfoFormat("Exiting findOne with value {0}", participant);
                        return participant;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Participant> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Participant> participants = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, name, date_of_birth from participants";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        string dateOfBirthString = dataR.GetString(2);
                        DateTime dateOfBirth = DateTime.Parse(dateOfBirthString);
                        Participant participant = new Participant(name, dateOfBirth); participant.Id = pid;
                        participants.Add(participant);
                    }
                }
            }
            return participants;
        }

        public void store(Participant entity)
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into participants(name, date_of_birth) values (@name, @date_of_birth)";
                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = entity.Name;
                comm.Parameters.Add(paramName);

                var paramDateOfBirth = comm.CreateParameter();
                paramDateOfBirth.ParameterName = "@date_of_birth";
                paramDateOfBirth.Value = entity.DateOfBirth.ToString();
                comm.Parameters.Add(paramDateOfBirth);
                try
                {
                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No participant added !");
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
        }

        public void delete(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from participants where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No participant deleted!");
            }
        }

        public void update(Participant entity)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Participant> getAllFromCompetition(int competitionId)
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Participant> participants = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,name,date_of_birth from participants p inner join registrations r " +
                    "on p.id=r.participant_id where r.competition_id=@competition_id";
                var pararmId = comm.CreateParameter();
                pararmId.ParameterName = "@competition_id";
                pararmId.Value = competitionId;
                comm.Parameters.Add(pararmId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        string dateOfBirthString = dataR.GetString(2);
                        DateTime dateOfBirth = DateTime.Parse(dateOfBirthString);
                        Participant participant = new Participant(name, dateOfBirth); participant.Id = pid;
                        participants.Add(participant);
                    }
                }
            }
            return participants;
        }

        public Participant getOneByName(string name)
        {
            log.InfoFormat("Entering getOneByName with value {0}", name);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, name, date_of_birth from participants where name=@name";
                IDbDataParameter paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = name;
                comm.Parameters.Add(paramName);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int pid = dataR.GetInt32(0);
                        string pName = dataR.GetString(1);
                        string dateOfBirthString = dataR.GetString(2);
                        DateTime dateOfBirth = DateTime.Parse(dateOfBirthString);
                        Participant participant = new Participant(name, dateOfBirth); participant.Id = pid;

                        log.InfoFormat("Exiting findOne with value {0}", participant);
                        return participant;
                    }
                }
            }
            log.InfoFormat("Exiting getOneByName with value {0}", null);
            return null;
        }
    }
}
