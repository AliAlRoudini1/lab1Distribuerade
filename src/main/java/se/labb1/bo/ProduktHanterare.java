package se.labb1.bo;

import se.labb1.db.ProduktDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProduktHanterare {

    private ProduktDB produktDB = new ProduktDB();

    public ArrayList<Produkt> hamtaAllaProdukter() throws DatabasFel {
        try {
            ArrayList<Produkt> produkter = produktDB.hamtaAllaProdukter();
            return produkter;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta produkterna från databasen.", fel);
        }
    }

    public Produkt hamtaProdukt(int id) throws DatabasFel {
        try {
            Produkt produkt = produktDB.hamtaProdukt(id);
            return produkt;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta produkten från databasen.", fel);
        }
    }

    public String fyllPaLager(Anvandare anvandare, int produktId, int antal) throws DatabasFel {
        if (anvandare.getRoll() != Roll.LAGER) {
            return "Bara lagerpersonal kan fylla på lagret.";
        }

        if (antal < 1 || antal > 10000) {
            return "Skriv ett heltal mellan 1 och 10000.";
        }

        try {
            boolean produktenFanns = produktDB.fyllPaLager(produktId, antal);
            if (!produktenFanns) {
                return "Produkten finns inte.";
            }
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte fylla på lagret.", fel);
        }

        return null;
    }
}
