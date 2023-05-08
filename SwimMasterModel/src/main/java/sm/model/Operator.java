package sm.model;

public class Operator extends Entity<Integer> {
    private String username, password;

    public Operator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Operator(int id, String username, String password){
        super.setId(id);
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
}
