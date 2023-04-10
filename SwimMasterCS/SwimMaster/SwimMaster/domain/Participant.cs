namespace SwimMaster.domain
{
    public class Participant : Entity<int>
    {
        private string name;
        private DateTime dateOfBirth;

        public Participant(string name, DateTime dateOfBirth)
        {
            this.name= name;
            this.dateOfBirth = dateOfBirth;
        }

        public string Name { get => name; set => name = value; }
        public DateTime DateOfBirth { get => dateOfBirth; set => dateOfBirth = value; }

        public int Varsta
        {
            get
            {
                return DateTime.Now.Year - dateOfBirth.Year;
            }
        }

        public override string ToString()
        {
            return name + " - " + this.Varsta + " ani";
        }
    }
}