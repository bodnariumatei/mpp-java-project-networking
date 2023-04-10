namespace SwimMaster.domain
{
    public class Competition : Entity<int>
    {
        private int distance;
        private string style;
        public Competition(int distance, string style) {
            this.distance = distance;
            this.style = style;
        }

        public int Distance { get => distance; set => distance = value; }
        public string Style { get => style; set => style = value; }

        public override string ToString()
        {
            return style + " " + distance.ToString()+"m";
        }
    }
}