namespace SwimMaster.gui
{
    partial class RegistrationScene
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
            label1 = new Label();
            nameTextBox = new TextBox();
            dateOfBirthDatePicker = new DateTimePicker();
            registrationButton = new Button();
            label2 = new Label();
            label3 = new Label();
            competitionsCheckList = new CheckedListBox();
            label4 = new Label();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Arial", 11.25F, FontStyle.Regular, GraphicsUnit.Point);
            label1.Location = new Point(71, 67);
            label1.Name = "label1";
            label1.Size = new Size(47, 17);
            label1.TabIndex = 0;
            label1.Text = "Nume";
            // 
            // nameTextBox
            // 
            nameTextBox.Location = new Point(128, 66);
            nameTextBox.Margin = new Padding(3, 2, 3, 2);
            nameTextBox.Name = "nameTextBox";
            nameTextBox.Size = new Size(196, 23);
            nameTextBox.TabIndex = 1;
            // 
            // dateOfBirthDatePicker
            // 
            dateOfBirthDatePicker.Location = new Point(128, 93);
            dateOfBirthDatePicker.Margin = new Padding(3, 2, 3, 2);
            dateOfBirthDatePicker.MaxDate = new DateTime(2030, 12, 31, 0, 0, 0, 0);
            dateOfBirthDatePicker.MinDate = new DateTime(1910, 12, 31, 0, 0, 0, 0);
            dateOfBirthDatePicker.Name = "dateOfBirthDatePicker";
            dateOfBirthDatePicker.Size = new Size(196, 23);
            dateOfBirthDatePicker.TabIndex = 2;
            dateOfBirthDatePicker.Value = new DateTime(2002, 12, 4, 0, 0, 0, 0);
            // 
            // registrationButton
            // 
            registrationButton.Location = new Point(242, 279);
            registrationButton.Margin = new Padding(3, 2, 3, 2);
            registrationButton.Name = "registrationButton";
            registrationButton.Size = new Size(82, 22);
            registrationButton.TabIndex = 3;
            registrationButton.Text = "Înscrie";
            registrationButton.UseVisualStyleBackColor = true;
            registrationButton.Click += registrationButton_Click;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Arial", 11.25F, FontStyle.Regular, GraphicsUnit.Point);
            label2.Location = new Point(26, 97);
            label2.Name = "label2";
            label2.Size = new Size(92, 17);
            label2.TabIndex = 4;
            label2.Text = "Data Nașterii";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Font = new Font("Impact", 18F, FontStyle.Regular, GraphicsUnit.Point);
            label3.Location = new Point(128, 21);
            label3.Name = "label3";
            label3.Size = new Size(104, 29);
            label3.TabIndex = 5;
            label3.Text = "Înscriere";
            // 
            // competitionsCheckList
            // 
            competitionsCheckList.FormattingEnabled = true;
            competitionsCheckList.Location = new Point(128, 121);
            competitionsCheckList.Name = "competitionsCheckList";
            competitionsCheckList.Size = new Size(196, 148);
            competitionsCheckList.TabIndex = 6;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Font = new Font("Arial", 11.25F, FontStyle.Regular, GraphicsUnit.Point);
            label4.Location = new Point(71, 121);
            label4.Name = "label4";
            label4.Size = new Size(47, 17);
            label4.TabIndex = 7;
            label4.Text = "Probe";
            // 
            // RegistrationScene
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(356, 330);
            Controls.Add(label4);
            Controls.Add(competitionsCheckList);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(registrationButton);
            Controls.Add(dateOfBirthDatePicker);
            Controls.Add(nameTextBox);
            Controls.Add(label1);
            Margin = new Padding(3, 2, 3, 2);
            Name = "RegistrationScene";
            Text = "RegistrationScene";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Label label1;
        private TextBox nameTextBox;
        private DateTimePicker dateOfBirthDatePicker;
        private Button registrationButton;
        private Label label2;
        private Label label3;
        private CheckedListBox competitionsCheckList;
        private Label label4;
    }
}