namespace SwimMaster.domain { 
    public class Entity<IDType>{
        private IDType id;

        public IDType Id { get => id; set => id = value; }
    }
}