package se.labb1.db;

import se.labb1.bo.Anvandare;
import se.labb1.bo.Roll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnvandareDB {

    public Anvandare hittaAnvandare(String anvandarnamn, String losenord) throws SQLException {
        String sql = "SELECT id, anvandarnamn, roll FROM anvandare WHERE anvandarnamn = ? AND losenord = ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setString(1, anvandarnamn);
            fraga.setString(2, losenord);

            try (ResultSet resultat = fraga.executeQuery()) {
                if (resultat.next()) {
                    return skapaAnvandare(resultat);
                } else {
                    return null;
                }
            }
        }
    }

    public ArrayList<Anvandare> hamtaAllaAnvandare() throws SQLException {
        String sql = "SELECT id, anvandarnamn, roll FROM anvandare ORDER BY anvandarnamn";
        ArrayList<Anvandare> allaAnvandare = new ArrayList<Anvandare>();

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql);
             ResultSet resultat = fraga.executeQuery()) {

            while (resultat.next()) {
                Anvandare anvandare = skapaAnvandare(resultat);
                allaAnvandare.add(anvandare);
            }
        }
        return allaAnvandare;
    }

    public void laggTillAnvandare(String anvandarnamn, String losenord, Roll roll) throws SQLException {
        String sql = "INSERT INTO anvandare (anvandarnamn, losenord, roll) VALUES (?, ?, ?)";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setString(1, anvandarnamn);
            fraga.setString(2, losenord);
            fraga.setString(3, roll.name());
            fraga.executeUpdate();
        }
    }

    public void andraRoll(int id, Roll roll) throws SQLException {
        String sql = "UPDATE anvandare SET roll = ? WHERE id = ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setString(1, roll.name());
            fraga.setInt(2, id);
            fraga.executeUpdate();
        }
    }

    public void taBortAnvandare(int id) throws SQLException {
        String sql = "DELETE FROM anvandare WHERE id = ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning();
             PreparedStatement fraga = anslutning.prepareStatement(sql)) {

            fraga.setInt(1, id);
            fraga.executeUpdate();
        }
    }

    private Anvandare skapaAnvandare(ResultSet resultat) throws SQLException {
        int id = resultat.getInt("id");
        String anvandarnamn = resultat.getString("anvandarnamn");
        Roll roll = Roll.valueOf(resultat.getString("roll"));
        return new Anvandare(id, anvandarnamn, roll);
    }
}
