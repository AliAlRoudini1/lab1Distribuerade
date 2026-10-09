package se.labb1.bo;

public class Anvandare {

    private int id;
    private String anvandarnamn;
    private Roll roll;

    public Anvandare(int id, String anvandarnamn, Roll roll) {
        this.id = id;
        this.anvandarnamn = anvandarnamn;
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public String getAnvandarnamn() {
        return anvandarnamn;
    }

    public Roll getRoll() {
        return roll;
    }

    public boolean isAdmin() {
        if (roll == Roll.ADMIN) {
            return true;
        } else {
            return false;
        }
    }
}
