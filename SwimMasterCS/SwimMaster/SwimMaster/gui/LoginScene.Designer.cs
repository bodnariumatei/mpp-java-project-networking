namespace SwimMaster
{
    partial class LoginScene
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            splitContainer1 = new SplitContainer();
            errorsLabel = new Label();
            loginButton = new Button();
            passwordTextBox = new TextBox();
            passwordLabel = new Label();
            label2 = new Label();
            usernameTextBox = new TextBox();
            label1 = new Label();
            ((System.ComponentModel.ISupportInitialize)splitContainer1).BeginInit();
            splitContainer1.Panel2.SuspendLayout();
            splitContainer1.SuspendLayout();
            SuspendLayout();
            // 
            // splitContainer1
            // 
            splitContainer1.Dock = DockStyle.Fill;
            splitContainer1.Location = new Point(0, 0);
            splitContainer1.Margin = new Padding(0);
            splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            splitContainer1.Panel1.BackColor = Color.DeepSkyBlue;
            // 
            // splitContainer1.Panel2
            // 
            splitContainer1.Panel2.BackColor = Color.White;
            splitContainer1.Panel2.Controls.Add(errorsLabel);
            splitContainer1.Panel2.Controls.Add(loginButton);
            splitContainer1.Panel2.Controls.Add(passwordTextBox);
            splitContainer1.Panel2.Controls.Add(passwordLabel);
            splitContainer1.Panel2.Controls.Add(label2);
            splitContainer1.Panel2.Controls.Add(usernameTextBox);
            splitContainer1.Panel2.Controls.Add(label1);
            splitContainer1.Size = new Size(500, 220);
            splitContainer1.SplitterDistance = 139;
            splitContainer1.SplitterWidth = 1;
            splitContainer1.TabIndex = 0;
            // 
            // errorsLabel
            // 
            errorsLabel.AutoSize = true;
            errorsLabel.Font = new Font("Arial", 9.75F, FontStyle.Italic, GraphicsUnit.Point);
            errorsLabel.ForeColor = Color.Maroon;
            errorsLabel.Location = new Point(34, 146);
            errorsLabel.MaximumSize = new Size(300, 0);
            errorsLabel.MinimumSize = new Size(300, 0);
            errorsLabel.Name = "errorsLabel";
            errorsLabel.Size = new Size(300, 16);
            errorsLabel.TabIndex = 6;
            errorsLabel.TextAlign = ContentAlignment.MiddleCenter;
            // 
            // loginButton
            // 
            loginButton.BackColor = Color.DodgerBlue;
            loginButton.FlatStyle = FlatStyle.Flat;
            loginButton.Font = new Font("Arial", 9.75F, FontStyle.Bold, GraphicsUnit.Point);
            loginButton.ForeColor = Color.White;
            loginButton.Location = new Point(108, 167);
            loginButton.Margin = new Padding(0);
            loginButton.Name = "loginButton";
            loginButton.Size = new Size(150, 30);
            loginButton.TabIndex = 5;
            loginButton.Text = "Login";
            loginButton.UseVisualStyleBackColor = false;
            loginButton.Click += loginButton_Click;
            // 
            // passwordTextBox
            // 
            passwordTextBox.Location = new Point(109, 118);
            passwordTextBox.Name = "passwordTextBox";
            passwordTextBox.Size = new Size(229, 23);
            passwordTextBox.TabIndex = 4;
            passwordTextBox.UseSystemPasswordChar = true;
            // 
            // passwordLabel
            // 
            passwordLabel.AutoSize = true;
            passwordLabel.Font = new Font("Arial", 12F, FontStyle.Regular, GraphicsUnit.Point);
            passwordLabel.Location = new Point(25, 120);
            passwordLabel.Name = "passwordLabel";
            passwordLabel.Size = new Size(78, 18);
            passwordLabel.TabIndex = 3;
            passwordLabel.Text = "Password";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Arial", 12F, FontStyle.Regular, GraphicsUnit.Point);
            label2.Location = new Point(23, 89);
            label2.Name = "label2";
            label2.Size = new Size(80, 18);
            label2.TabIndex = 2;
            label2.Text = "Username";
            // 
            // usernameTextBox
            // 
            usernameTextBox.Location = new Point(109, 87);
            usernameTextBox.Name = "usernameTextBox";
            usernameTextBox.Size = new Size(229, 23);
            usernameTextBox.TabIndex = 1;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.BackColor = Color.White;
            label1.Font = new Font("Ceviche One", 36F, FontStyle.Regular, GraphicsUnit.Point);
            label1.ForeColor = Color.DodgerBlue;
            label1.Location = new Point(58, 18);
            label1.Name = "label1";
            label1.Size = new Size(241, 50);
            label1.TabIndex = 0;
            label1.Text = "SwimMaster";
            // 
            // LoginScene
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(500, 220);
            Controls.Add(splitContainer1);
            Name = "LoginScene";
            Text = "Login";
            splitContainer1.Panel2.ResumeLayout(false);
            splitContainer1.Panel2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)splitContainer1).EndInit();
            splitContainer1.ResumeLayout(false);
            ResumeLayout(false);
        }

        #endregion

        private SplitContainer splitContainer1;
        private TextBox passwordTextBox;
        private Label passwordLabel;
        private Label label2;
        private TextBox usernameTextBox;
        private Label label1;
        private Button loginButton;
        private Label errorsLabel;
    }
}