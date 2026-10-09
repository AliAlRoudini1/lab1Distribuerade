package se.labb1.bo;

import java.io.Serializable;

public class AnvandareInfo implements Serializable {

    private final int id;
    private final String anvandarnamn;
    private final Roll roll;
    private final boolean admin;

    public AnvandareInfo(int id, String anvandarnamn, Roll roll, boolean admin) {
        this.id = id;
        this.anvandarnamn = anvandarnamn;
        this.roll = roll;
        this.admin = admin;
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
        return admin;
    }
}
