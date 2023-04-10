using SwimMaster.domain;
using SwimMaster.gui;
using SwimMaster.service;

namespace SwimMaster
{
    public partial class LoginScene : Form
    {
        private OperatorService _srv;
        public LoginScene()
        {
            InitializeComponent();
            this._srv = new OperatorService();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            if (usernameTextBox.Text == "" || passwordTextBox.Text == "")
            {
                errorsLabel.Text = "Username and password can't be empty!";
                return;
            }
            Operator op = this._srv.GetOperator(usernameTextBox.Text);
            if (op == null)
            {
                errorsLabel.Text = "Wrong username or password!";
                return;
            }

            if (op.Password != passwordTextBox.Text)
            {
                errorsLabel.Text = "Wrong username or password!";
                return;
            }

            usernameTextBox.Clear();
            passwordTextBox.Clear();
            errorsLabel.Text = "";

            this.Hide();
            MainScene mainScene = new MainScene(this);

            mainScene.Show();
        }
    }
}