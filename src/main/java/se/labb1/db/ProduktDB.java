package se.labb1.db;

import se.labb1.bo.Produkt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProduktDB {

    public ArrayList<Produkt> hamtaAllaProdukter() throws SQLException {
        String sql = "SELECT id, namn, beskrivning, pris, lager_antal FROM produkt ORDER BY namn";
        ArrayList<Produkt> produkter = new ArrayList<Produkt>();

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql);
             ResultSet resultat = fraga.executeQuery()) {

            while (resultat.next()) {
                Produkt produkt = skapaProdukt(resultat);
                produkter.add(produkt);
            }
        }
        return produkter;
    }

    public Produkt hamtaProdukt(int id) throws SQLException {
        String sql = "SELECT id, namn, beskrivning, pris, lager_antal FROM produkt WHERE id = ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setInt(1, id);

            try (ResultSet resultat = fraga.executeQuery()) {
                if (resultat.next()) {
                    return skapaProdukt(resultat);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean fyllPaLager(int produktId, int antal) throws SQLException {

        String sql = "UPDATE produkt SET lager_antal = lager_antal + ? WHERE id = ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setInt(1, antal);
            fraga.setInt(2, produktId);
            int andradeRader = fraga.executeUpdate();

            if (andradeRader == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    private Produkt skapaProdukt(ResultSet resultat) throws SQLException {
        int id = resultat.getInt("id");
        String namn = resultat.getString("namn");
        String beskrivning = resultat.getString("beskrivning");
        int pris = resultat.getInt("pris");
        int lagerAntal = resultat.getInt("lager_antal");
        return new Produkt(id, namn, beskrivning, pris, lagerAntal);
    }
}
