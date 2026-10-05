package se.labb1.db;

import se.labb1.bo.Produkt;
import se.labb1.bo.Varukorg;
import se.labb1.bo.VarukorgRad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderDB {

    public int skapaOrder(int anvandareId, Varukorg varukorg) throws SQLException {
        String sqlOrder = "INSERT INTO ordrar (anvandare_id) VALUES (?)";
        String sqlOrderrad = "INSERT INTO orderrader (order_id, produkt_id, antal, pris) VALUES (?, ?, ?, ?)";
        String sqlLager = "UPDATE produkt SET lager_antal = lager_antal - ? WHERE id = ? AND lager_antal >= ?";

        try (Connection anslutning = DatabasAnslutning.hamtaAnslutning()) {

            anslutning.setAutoCommit(false);

            try (PreparedStatement orderFraga = anslutning.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement orderradFraga = anslutning.prepareStatement(sqlOrderrad);
                 PreparedStatement lagerFraga = anslutning.prepareStatement(sqlLager)) {

                orderFraga.setInt(1, anvandareId);
                orderFraga.executeUpdate();
                int orderId;
                try (ResultSet nycklar = orderFraga.getGeneratedKeys()) {
                    nycklar.next();
                    orderId = nycklar.getInt(1);
                }

                ArrayList<VarukorgRad> rader = varukorg.getRader();
                for (int i = 0; i < rader.size(); i++) {
                    VarukorgRad rad = rader.get(i);
                    Produkt produkt = rad.getProdukt();

                    orderradFraga.setInt(1, orderId);
                    orderradFraga.setInt(2, produkt.getId());
                    orderradFraga.setInt(3, rad.getAntal());
                    orderradFraga.setInt(4, produkt.getPris());
                    orderradFraga.executeUpdate();

                    lagerFraga.setInt(1, rad.getAntal());
                    lagerFraga.setInt(2, produkt.getId());
                    lagerFraga.setInt(3, rad.getAntal());
                    int andradeRader = lagerFraga.executeUpdate();

                    if (andradeRader == 0) {
                        anslutning.rollback();
                        return 0;
                    }
                }

                anslutning.commit();
                return orderId;

            } catch (SQLException fel) {
                anslutning.rollback();
                throw fel;
            }
        }
    }
}
