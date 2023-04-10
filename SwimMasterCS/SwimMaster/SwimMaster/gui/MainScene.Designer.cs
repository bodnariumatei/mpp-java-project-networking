namespace SwimMaster.gui
{
    partial class MainScene
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
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
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            competitionsListView = new ListView();
            label1 = new Label();
            label2 = new Label();
            participantsListView = new ListView();
            menuStrip1 = new MenuStrip();
            înscrieriToolStripMenuItem = new ToolStripMenuItem();
            logoutButton = new ToolStripMenuItem();
            menuStrip1.SuspendLayout();
            SuspendLayout();
            // 
            // competitionsListView
            // 
            competitionsListView.Font = new Font("Arial", 10.8F, FontStyle.Regular, GraphicsUnit.Point);
            competitionsListView.FullRowSelect = true;
            competitionsListView.GridLines = true;
            competitionsListView.Location = new Point(14, 60);
            competitionsListView.Margin = new Padding(3, 4, 3, 4);
            competitionsListView.MultiSelect = false;
            competitionsListView.Name = "competitionsListView";
            competitionsListView.Size = new Size(371, 500);
            competitionsListView.TabIndex = 0;
            competitionsListView.UseCompatibleStateImageBehavior = false;
            competitionsListView.View = View.List;
            competitionsListView.SelectedIndexChanged += competitionsListView_SelectedIndexChanged;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Arial Narrow", 21.75F, FontStyle.Bold, GraphicsUnit.Point);
            label1.Location = new Point(14, 12);
            label1.Name = "label1";
            label1.Size = new Size(106, 43);
            label1.TabIndex = 2;
            label1.Text = "Probe";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Arial Narrow", 21.75F, FontStyle.Bold, GraphicsUnit.Point);
            label2.Location = new Point(403, 12);
            label2.Name = "label2";
            label2.Size = new Size(184, 43);
            label2.TabIndex = 3;
            label2.Text = "Participanți";
            // 
            // participantsListView
            // 
            participantsListView.Font = new Font("Arial", 9.75F, FontStyle.Regular, GraphicsUnit.Point);
            participantsListView.Location = new Point(403, 60);
            participantsListView.Margin = new Padding(3, 4, 3, 4);
            participantsListView.Name = "participantsListView";
            participantsListView.Size = new Size(611, 500);
            participantsListView.TabIndex = 4;
            participantsListView.UseCompatibleStateImageBehavior = false;
            participantsListView.View = View.List;
            // 
            // menuStrip1
            // 
            menuStrip1.BackColor = Color.PowderBlue;
            menuStrip1.Dock = DockStyle.Right;
            menuStrip1.ImageScalingSize = new Size(20, 20);
            menuStrip1.Items.AddRange(new ToolStripItem[] { înscrieriToolStripMenuItem, logoutButton });
            menuStrip1.Location = new Point(1012, 0);
            menuStrip1.Name = "menuStrip1";
            menuStrip1.Padding = new Padding(7, 3, 0, 3);
            menuStrip1.Size = new Size(157, 589);
            menuStrip1.TabIndex = 5;
            menuStrip1.Text = "menuStrip1";
            // 
            // înscrieriToolStripMenuItem
            // 
            înscrieriToolStripMenuItem.BackColor = Color.DeepSkyBlue;
            înscrieriToolStripMenuItem.Font = new Font("Arial", 11.25F, FontStyle.Regular, GraphicsUnit.Point);
            înscrieriToolStripMenuItem.ForeColor = Color.Navy;
            înscrieriToolStripMenuItem.Margin = new Padding(0, 5, 0, 5);
            înscrieriToolStripMenuItem.Name = "înscrieriToolStripMenuItem";
            înscrieriToolStripMenuItem.Padding = new Padding(15, 10, 15, 10);
            înscrieriToolStripMenuItem.Size = new Size(142, 46);
            înscrieriToolStripMenuItem.Text = "Înscrieri";
            înscrieriToolStripMenuItem.Click += înscrieriToolStripMenuItem_Click;
            // 
            // logoutButton
            // 
            logoutButton.BackColor = Color.DeepSkyBlue;
            logoutButton.Font = new Font("Arial", 11.25F, FontStyle.Regular, GraphicsUnit.Point);
            logoutButton.ForeColor = Color.Navy;
            logoutButton.Margin = new Padding(0, 5, 0, 5);
            logoutButton.Name = "logoutButton";
            logoutButton.Padding = new Padding(15, 10, 15, 10);
            logoutButton.Size = new Size(142, 46);
            logoutButton.Text = "Logout";
            logoutButton.Click += logoutButton_Click;
            // 
            // MainScene
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1169, 589);
            Controls.Add(participantsListView);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(competitionsListView);
            Controls.Add(menuStrip1);
            Name = "MainScene";
            Text = "MainScene";
            menuStrip1.ResumeLayout(false);
            menuStrip1.PerformLayout();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private ListView competitionsListView;
        private Label label1;
        private Label label2;
        private ListView participantsListView;
        private MenuStrip menuStrip1;
        private ToolStripMenuItem înscrieriToolStripMenuItem;
        private ToolStripMenuItem logoutButton;
    }
}