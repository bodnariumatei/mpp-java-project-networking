package sm.model.utils;

public class ParticipantItem {
    private String name;
    private int age;
    private String competitions;

    public ParticipantItem(String name, int age, String competitions) {
        this.name = name;
        this.age = age;
        this.competitions = competitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCompetitions() {
        return competitions;
    }

    public void setCompetitions(String competitions) {
        this.competitions = competitions;
    }
}
