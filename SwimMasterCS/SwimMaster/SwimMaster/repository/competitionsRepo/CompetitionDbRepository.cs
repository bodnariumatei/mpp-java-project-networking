using log4net;
using SwimMaster.domain;
using System.Data;
using tasks.repository;

namespace SwimMaster.repository.competitionsRepo
{
    internal class CompetitionDbRepository : ICompetitionDbRepository
    {
        private static readonly ILog log = LogManager.GetLogger("OperatorDbRepository");
        IDictionary<string, string> props;

        public CompetitionDbRepository(IDictionary<string, string> props) {
            log.Info("Creating CompetitionDbRepository ");
            this.props = props;
        }

        public Competition getOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, distance, style from competitions where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int distance = dataR.GetInt32(1);
                        string style = dataR.GetString(2);
                        Competition competition = new Competition(distance, style);
                        competition.Id = cid;

                        log.InfoFormat("Exiting findOne with value {0}", competition);
                        return competition;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Competition> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Competition> competitions = new List<Competition>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, distance, style from competitions";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int distance = dataR.GetInt32(1);
                        string style = dataR.GetString(2);
                        Competition competition = new Competition(distance, style); competition.Id = cid;
                        competitions.Add(competition);
                    }
                }
            }
            return competitions;
        }

        public void store(Competition entity)
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into competitions(distance, style) values (@distance, @style)";
                var paramDistance = comm.CreateParameter();
                paramDistance.ParameterName = "@distance";
                paramDistance.Value = entity.Distance;
                comm.Parameters.Add(paramDistance);

                var paramStyle = comm.CreateParameter();
                paramStyle.ParameterName = "@style";
                paramStyle.Value = entity.Style;
                comm.Parameters.Add(paramStyle);
                try
                {
                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No competition added !");
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
                comm.CommandText = "delete from competitions where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No competition deleted!");
            }
        }

        public void update(Competition entity)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<(Competition, int)> GetAllWithNoParticipants()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<(Competition, int)> competitions = new List<(Competition, int)>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, distance, style, COUNT(participant_id) as no_part " +
                    "from competitions c INNER JOIN registrations r ON c.id=r.competition_id " +
                    "GROUP BY id,style, distance";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int distance = dataR.GetInt32(1);
                        string style = dataR.GetString(2);
                        int noParticipants = dataR.GetInt32(3);
                        Competition competition = new Competition(distance, style); competition.Id = cid;
                        competitions.Add((competition, noParticipants));
                    }
                }
            }
            return competitions;
        }

        public IEnumerable<Competition> GetAllForParticipant(int participantId)
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Competition> competitions = new List<Competition>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, distance, style from competitions c INNER JOIN registrations r " +
                    "ON c.id=r.competition_id where r.participant_id = @participant_id";
                var pararmId = comm.CreateParameter();
                pararmId.ParameterName = "@participant_id";
                pararmId.Value = participantId;
                comm.Parameters.Add(pararmId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int cid = dataR.GetInt32(0);
                        int distance = dataR.GetInt32(1);
                        string style = dataR.GetString(2);
                        Competition competition = new Competition(distance, style); competition.Id = cid;
                        competitions.Add(competition);
                    }
                }
            }
            return competitions;
        }

        public void registerParticipantAtCompetition(int participantId, int competitionId)
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into registrations(participant_id, competition_id) " +
                    "values (@participant_id, @competition_id)";
                var paramParticipantId = comm.CreateParameter();
                paramParticipantId.ParameterName = "@participant_id";
                paramParticipantId.Value = participantId;
                comm.Parameters.Add(paramParticipantId);

                var paramCompetitionId = comm.CreateParameter();
                paramCompetitionId.ParameterName = "@competition_id";
                paramCompetitionId.Value = competitionId;
                comm.Parameters.Add(paramCompetitionId);
                try
                {
                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No registration added !");
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
        }
    }
}
