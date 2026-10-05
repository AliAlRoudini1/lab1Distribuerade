package se.labb1.bo;

import se.labb1.db.AnvandareDB;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class AnvandarHanterare {

    private AnvandareDB anvandareDB = new AnvandareDB();

    public Anvandare loggaIn(String anvandarnamn, String losenord) throws DatabasFel {
        try {
            Anvandare anvandare = anvandareDB.hittaAnvandare(anvandarnamn, losenord);
            return anvandare;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte kontrollera inloggningen mot databasen.", fel);
        }
    }

    public ArrayList<Anvandare> hamtaAllaAnvandare() throws DatabasFel {
        try {
            ArrayList<Anvandare> anvandare = anvandareDB.hamtaAllaAnvandare();
            return anvandare;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta användarna från databasen.", fel);
        }
    }

    public void laggTillAnvandare(String anvandarnamn, String losenord, Roll roll) throws DatabasFel {
        try {
            anvandareDB.laggTillAnvandare(anvandarnamn, losenord, roll);
        } catch (SQLIntegrityConstraintViolationException fel) {
            throw new DatabasFel("Användarnamnet " + anvandarnamn + " finns redan.", fel);
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte lägga till användaren.", fel);
        }
    }

    public void andraRoll(int id, Roll roll) throws DatabasFel {
        try {
            anvandareDB.andraRoll(id, roll);
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte ändra rollen.", fel);
        }
    }

    public void taBortAnvandare(int id) throws DatabasFel {
        try {
            anvandareDB.taBortAnvandare(id);
        } catch (SQLIntegrityConstraintViolationException fel) {
            throw new DatabasFel("Användaren har lagt ordrar och kan inte tas bort.", fel);
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte ta bort användaren.", fel);
        }
    }
}
