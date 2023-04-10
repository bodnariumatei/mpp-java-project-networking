using log4net.Config;
using SwimMaster.domain;
using System.Configuration;
using SwimMaster.repository.operatorsRepo;
using SwimMaster.service;

namespace SwimMaster
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]

        private static void Main(string[] args)
        {
            ApplicationConfiguration.Initialize();
            Application.Run(new LoginScene());
        }
    }
}