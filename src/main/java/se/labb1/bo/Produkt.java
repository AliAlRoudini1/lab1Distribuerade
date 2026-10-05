package se.labb1.bo;

import java.io.Serializable;

public class Produkt implements Serializable {

    private int id;
    private String namn;
    private String beskrivning;
    private int pris;
    private int lagerAntal;

    public Produkt(int id, String namn, String beskrivning, int pris, int lagerAntal) {
        this.id = id;
        this.namn = namn;
        this.beskrivning = beskrivning;
        this.pris = pris;
        this.lagerAntal = lagerAntal;
    }

    public int getId() {
        return id;
    }

    public String getNamn() {
        return namn;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public int getPris() {
        return pris;
    }

    public int getLagerAntal() {
        return lagerAntal;
    }
}
