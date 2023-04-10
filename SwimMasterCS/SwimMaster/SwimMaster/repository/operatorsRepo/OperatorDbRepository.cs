using log4net;
using SwimMaster.domain;
using System.Data;
using tasks.repository;

namespace SwimMaster.repository.operatorsRepo
{
    internal class OperatorDbRepository : IOperatorDbRepository
    {
        private static readonly ILog log = LogManager.GetLogger("OperatorDbRepository");

        IDictionary<string, string> props;

        public OperatorDbRepository(IDictionary<string, string> props)
        {
            log.Info("Creating OperatorDbRepository ");
            this.props = props;
        }

        public Operator getOneByUsername(string username)
        {
            log.InfoFormat("Entering findOne with value {0}", username);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, username, password from operators where username=@username";
                IDbDataParameter paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                comm.Parameters.Add(paramUsername);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int oid = dataR.GetInt32(0);
                        string oUsername = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        Operator op = new Operator(username, password);
                        op.Id = oid;

                        log.InfoFormat("Exiting findOne with value {0}", op);
                        return op;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Operator getOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, username, password from operators where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int oid = dataR.GetInt32(0);
                        string username = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        Operator op = new Operator(username, password);
                        op.Id = oid;

                        log.InfoFormat("Exiting findOne with value {0}", op);
                        return op;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public IEnumerable<Operator> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Operator> operators = new List<Operator>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, username, password from operators";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string username = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        Operator op = new Operator(username, password); 
                        op.Id = id;
                        operators.Add(op);
                    }
                }
            }
            return operators;
        }

        public void store(Operator entity)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into operators(username, password) values (@username, @password)";
                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = entity.Username;
                comm.Parameters.Add(paramUsername);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = entity.Password;
                comm.Parameters.Add(paramPassword);
                try
                {
                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No operator added !");
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
        }

        public void update(Operator entity)
        {
            throw new NotImplementedException();
        }

        public void delete(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from operators where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    throw new Exception("No operator deleted!");
            }
        }
    }
}
