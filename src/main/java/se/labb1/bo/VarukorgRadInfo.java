package se.labb1.bo;

import java.io.Serializable;

public class VarukorgRadInfo implements Serializable {

    private final ProduktInfo produkt;
    private final int antal;
    private final int radSumma;

    public VarukorgRadInfo(ProduktInfo produkt, int antal, int radSumma) {
        this.produkt = produkt;
        this.antal = antal;
        this.radSumma = radSumma;
    }

    public ProduktInfo getProdukt() {
        return produkt;
    }

    public int getAntal() {
        return antal;
    }

    public int getRadSumma() {
        return radSumma;
    }
}
