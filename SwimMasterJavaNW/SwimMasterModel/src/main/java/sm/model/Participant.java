package sm.model;

import java.time.LocalDateTime;

public class Participant extends Entity<Integer> {
    private String name;
    private LocalDateTime dateOfBirth;
    public Participant(String name, LocalDateTime dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return LocalDateTime.now().getYear() - dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " - age: " + this.getAge();
    }
}
