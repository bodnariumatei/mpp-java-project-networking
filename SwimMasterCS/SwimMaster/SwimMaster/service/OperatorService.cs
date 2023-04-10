using log4net.Config;
using SwimMaster.domain;
using SwimMaster.repository.operatorsRepo;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tasks.repository;

namespace SwimMaster.service
{
    internal class OperatorService
    {
        private OperatorDbRepository opRepo;

        public OperatorService()
        {
            XmlConfigurator.Configure(new FileInfo("app.config"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", DBUtils.GetConnectionStringByName("swimMasterDB"));

            opRepo = new OperatorDbRepository(props);
        }

        public Operator GetOperator(String username){
            return opRepo.getOneByUsername(username);
        }
    }
}
