using SwimMaster.domain;
using SwimMaster.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SwimMaster.gui
{
    public partial class MainScene : Form
    {
        private LoginScene parentScene;
        private ContestService _srv;
        public MainScene(LoginScene parentScene)
        {
            InitializeComponent();
            this.parentScene = parentScene;
            this._srv = new ContestService();

            loadCompetitionsListView();
        }

        private void loadCompetitionsListView()
        {
            competitionsListView.Items.Clear();
            IEnumerable<(Competition, int)> competitions = _srv.getCompetitionsWithNoParticipants();
            foreach (var competition in competitions)
            {
                string text = competition.Item1.Id.ToString() + " - "
                    + competition.Item1.ToString() + " - "
                    + competition.Item2.ToString() + " participanti";
                competitionsListView.Items.Add(text);
            }
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            parentScene.Show();
            this.Close();
        }

        private void competitionsListView_SelectedIndexChanged(object sender, EventArgs e)
        {
            participantsListView.Items.Clear();
            if (competitionsListView.SelectedItems.Count != 0)
            {
                string text = competitionsListView.SelectedItems[0].Text;
                int competitionId = Int32.Parse(text.Split(" - ")[0]);

                IEnumerable<Participant> participants = _srv.getParticipantsFromCompetition(competitionId);
                foreach (Participant participant in participants)
                {
                    string participantString = participant.ToString() + " - ";
                    IEnumerable<Competition> competitions = _srv.GetCompetitionsForParticipant(participant.Id);
                    foreach (Competition competition in competitions)
                    {
                        participantString += competition.ToString() + ", ";
                    }

                    participantsListView.Items.Add(participantString);
                }
            }
        }

        private void înscrieriToolStripMenuItem_Click(object sender, EventArgs e)
        {
            RegistrationScene registrationScene = new RegistrationScene(this);
            registrationScene.Show();
        }

        internal void update()
        {
            loadCompetitionsListView();
        }
    }
}
