package sm.model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "operators")
public class Operator{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Operator() {}
    public Operator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Operator(int id, String username, String password){
        this.id = id;
        this.username=username;
        this.password=password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username " + this.username + " - password: " + this.password;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }
}
