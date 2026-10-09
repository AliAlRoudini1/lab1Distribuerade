package se.labb1.bo;

import se.labb1.db.OrderDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderHanterare {

    private OrderDB orderDB = new OrderDB();

    public VarukorgInfo skapaTomVarukorg() {
        Varukorg varukorg = new Varukorg();
        VarukorgInfo varukorgInfo = skapaVarukorgInfo(varukorg);
        return varukorgInfo;
    }

    public VarukorgInfo laggTillIVarukorg(VarukorgInfo varukorgInfo, ProduktInfo produktInfo, int antal) {
        Varukorg varukorg = skapaVarukorg(varukorgInfo);
        Produkt produkt = skapaProdukt(produktInfo);

        boolean lagdesTill = varukorg.laggTill(produkt, antal);
        if (!lagdesTill) {
            return null;
        }

        VarukorgInfo nyVarukorgInfo = skapaVarukorgInfo(varukorg);
        return nyVarukorgInfo;
    }

    public int skickaOrder(AnvandareInfo anvandare, VarukorgInfo varukorgInfo) throws DatabasFel {
        if (anvandare.getRoll() != Roll.KUND) {
            return -1;
        }

        Varukorg varukorg = skapaVarukorg(varukorgInfo);
        if (varukorg.isTom()) {
            throw new DatabasFel("Varukorgen är tom.", null);
        }

        try {
            int orderId = orderDB.skapaOrder(anvandare.getId(), varukorg);
            return orderId;
        } catch (SQLException fel) {
            throw new DatabasFel("Kunde inte skicka ordern. Ingenting har sparats.", fel);
        }
    }

    private Varukorg skapaVarukorg(VarukorgInfo varukorgInfo) {
        Varukorg varukorg = new Varukorg();
        ArrayList<VarukorgRadInfo> radInfoLista = varukorgInfo.getRader();
        for (int i = 0; i < radInfoLista.size(); i = i + 1) {
            VarukorgRadInfo radInfo = radInfoLista.get(i);
            Produkt produkt = skapaProdukt(radInfo.getProdukt());
            varukorg.laggTillRad(produkt, radInfo.getAntal());
        }
        return varukorg;
    }

    private VarukorgInfo skapaVarukorgInfo(Varukorg varukorg) {
        ArrayList<VarukorgRadInfo> radInfoLista = new ArrayList<VarukorgRadInfo>();
        ArrayList<VarukorgRad> rader = varukorg.getRader();
        for (int i = 0; i < rader.size(); i = i + 1) {
            VarukorgRad rad = rader.get(i);
            Produkt produkt = rad.getProdukt();
            ProduktInfo produktInfo = new ProduktInfo(produkt.getId(), produkt.getNamn(),
                    produkt.getBeskrivning(), produkt.getPris(), produkt.getLagerAntal());
            VarukorgRadInfo radInfo = new VarukorgRadInfo(produktInfo, rad.getAntal(), rad.getRadSumma());
            radInfoLista.add(radInfo);
        }

        VarukorgInfo varukorgInfo = new VarukorgInfo(radInfoLista, varukorg.getTotalSumma(),
                varukorg.getAntalVaror(), varukorg.isTom());
        return varukorgInfo;
    }

    private Produkt skapaProdukt(ProduktInfo produktInfo) {
        Produkt produkt = new Produkt(produktInfo.getId(), produktInfo.getNamn(),
                produktInfo.getBeskrivning(), produktInfo.getPris(), produktInfo.getLagerAntal());
        return produkt;
    }
}
