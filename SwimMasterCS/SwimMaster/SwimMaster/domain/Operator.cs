namespace SwimMaster.domain
{
    public class Operator : Entity<int>
    {
        private string username;
        private string password;

        public Operator(string username, string password)
        {
            this.username = username;
            this.password = password;
        }

        public string Username
        {
            get { return this.username; }
            set { this.username = value; }
        }
        public string Password
        {
            get { return this.password; }
            set { this.password = value; }
        }

        public override string ToString()
        {
            return this.username + " : " + this.password;
        }
    }
}