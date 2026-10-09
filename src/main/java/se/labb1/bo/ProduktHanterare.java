package se.labb1.bo;

import se.labb1.db.ProduktDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProduktHanterare {

    private ProduktDB produktDB = new ProduktDB();

    public ArrayList<ProduktInfo> hamtaAllaProdukter() throws DatabasFel {
        ArrayList<Produkt> produkter;
        try {
            produkter = produktDB.hamtaAllaProdukter();
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta produkterna från databasen.", fel);
        }

        ArrayList<ProduktInfo> produktInfoLista = new ArrayList<ProduktInfo>();
        for (int i = 0; i < produkter.size(); i = i + 1) {
            Produkt produkt = produkter.get(i);
            ProduktInfo produktInfo = skapaProduktInfo(produkt);
            produktInfoLista.add(produktInfo);
        }
        return produktInfoLista;
    }

    public ProduktInfo hamtaProdukt(int id) throws DatabasFel {
        Produkt produkt;
        try {
            produkt = produktDB.hamtaProdukt(id);
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte hämta produkten från databasen.", fel);
        }

        if (produkt == null) {
            return null;
        }

        ProduktInfo produktInfo = skapaProduktInfo(produkt);
        return produktInfo;
    }

    public String fyllPaLager(AnvandareInfo anvandare, int produktId, int antal) throws DatabasFel {
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

    private ProduktInfo skapaProduktInfo(Produkt produkt) {
        ProduktInfo produktInfo = new ProduktInfo(produkt.getId(), produkt.getNamn(), produkt.getBeskrivning(),
                produkt.getPris(), produkt.getLagerAntal());
        return produktInfo;
    }
}
