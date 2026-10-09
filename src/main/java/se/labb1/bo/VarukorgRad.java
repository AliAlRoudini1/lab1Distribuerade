package se.labb1.bo;

public class VarukorgRad {

    private Produkt produkt;
    private int antal;

    public VarukorgRad(Produkt produkt, int antal) {
        this.produkt = produkt;
        this.antal = antal;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public int getRadSumma() {
        int radSumma = produkt.getPris() * antal;
        return radSumma;
    }
}
