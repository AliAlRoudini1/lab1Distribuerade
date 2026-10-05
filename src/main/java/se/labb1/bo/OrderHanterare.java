package se.labb1.bo;

import se.labb1.db.OrderDB;

import java.sql.SQLException;

public class OrderHanterare {

    private OrderDB orderDB = new OrderDB();

    public int skickaOrder(Anvandare anvandare, Varukorg varukorg) throws DatabasFel {
        if (anvandare.getRoll() != Roll.KUND) {
            return -1;
        }

        try {
            int orderId = orderDB.skapaOrder(anvandare.getId(), varukorg);
            return orderId;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte skicka ordern. Ingenting har sparats.", fel);
        }
    }
}
