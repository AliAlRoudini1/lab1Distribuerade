package se.labb1.bo;

import se.labb1.db.AnvandareDB;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class AnvandarHanterare {

    private AnvandareDB anvandareDB = new AnvandareDB();

    public AnvandareInfo loggaIn(String anvandarnamn, String losenord) throws DatabasFel {
        Anvandare anvandare;
        try {
            anvandare = anvandareDB.hittaAnvandare(anvandarnamn, losenord);
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte kontrollera inloggningen mot databasen.", fel);
        }

        if (anvandare == null) {
            return null;
        }

        AnvandareInfo anvandareInfo = skapaAnvandareInfo(anvandare);
        return anvandareInfo;
    }

    public ArrayList<AnvandareInfo> hamtaAllaAnvandare() throws DatabasFel {
        ArrayList<Anvandare> allaAnvandare;
        try {
            allaAnvandare = anvandareDB.hamtaAllaAnvandare();
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta användarna från databasen.", fel);
        }

        ArrayList<AnvandareInfo> anvandareInfoLista = new ArrayList<AnvandareInfo>();
        for (int i = 0; i < allaAnvandare.size(); i = i + 1) {
            Anvandare anvandare = allaAnvandare.get(i);
            AnvandareInfo anvandareInfo = skapaAnvandareInfo(anvandare);
            anvandareInfoLista.add(anvandareInfo);
        }
        return anvandareInfoLista;
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

    private AnvandareInfo skapaAnvandareInfo(Anvandare anvandare) {
        AnvandareInfo anvandareInfo = new AnvandareInfo(anvandare.getId(), anvandare.getAnvandarnamn(),
                anvandare.getRoll(), anvandare.isAdmin());
        return anvandareInfo;
    }
}
