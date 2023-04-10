using SwimMaster.domain;
using SwimMaster.service;
using System;
using System.Collections;
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
    public partial class RegistrationScene : Form
    {
        private ContestService _srv;
        MainScene _scene;
        public RegistrationScene(MainScene parentScene)
        {
            InitializeComponent();
            this._srv = new ContestService();
            loadCompetitionsCheckList();
            _scene = parentScene;
        }

        private void loadCompetitionsCheckList()
        {
            competitionsCheckList.Items.Clear();
            competitionsCheckList.CheckOnClick = true;
            IEnumerable<Competition> competitions = _srv.getCompetitions();
            foreach (Competition competition in competitions)
            {
                competitionsCheckList.Items.Add(competition);
            }
        }

        private void registrationButton_Click(object sender, EventArgs e)
        {
            string name = nameTextBox.Text;
            if(name == "") {
                MessageBox.Show("Completați numele!");
                return;
            }
            DateTime date = dateOfBirthDatePicker.Value;
            _srv.storeParticipant(name, date);
            IEnumerator enumerator = competitionsCheckList.CheckedItems.GetEnumerator();
            try
            {
                int ok = 0;
                while (enumerator.MoveNext())
                {
                    ok = 1;
                    Competition competition = (Competition)enumerator.Current;
                    _srv.registerParticipantAtCompetition(name, competition.Id);
                }
                if(ok == 0) {
                    MessageBox.Show("Selectați probe!");
                    return;
                }
                _scene.update();
            } catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
