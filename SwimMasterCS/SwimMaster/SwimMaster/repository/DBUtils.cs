using System.Configuration;
using System.Data;
using ConnectionUtils;

namespace tasks.repository
{
    public static class DBUtils
	{
		

		private static IDbConnection instance = null;


		public static IDbConnection getConnection(IDictionary<string,string> props)
		{
			if (instance == null || instance.State == System.Data.ConnectionState.Closed)
			{
				instance = getNewConnection(props);
				instance.Open();
			}
			return instance;
		}

		private static IDbConnection getNewConnection(IDictionary<string,string> props)
		{
			
			return ConnectionFactory.getInstance().createConnection(props);

		}

        public static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}
