package se.labb1.bo;

import java.io.Serializable;

public class ProduktInfo implements Serializable {

    private final int id;
    private final String namn;
    private final String beskrivning;
    private final int pris;
    private final int lagerAntal;

    public ProduktInfo(int id, String namn, String beskrivning, int pris, int lagerAntal) {
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
