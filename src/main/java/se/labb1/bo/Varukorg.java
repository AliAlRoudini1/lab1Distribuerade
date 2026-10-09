package se.labb1.bo;

import java.util.ArrayList;

public class Varukorg {

    private ArrayList<VarukorgRad> rader = new ArrayList<VarukorgRad>();

    public boolean laggTill(Produkt produkt, int antal) {
        if (antal < 1) {
            return false;
        }

        VarukorgRad befintligRad = null;
        for (int i = 0; i < rader.size(); i++) {
            VarukorgRad rad = rader.get(i);
            if (rad.getProdukt().getId() == produkt.getId()) {
                befintligRad = rad;
            }
        }

        int antalIKorgen = 0;
        if (befintligRad != null) {
            antalIKorgen = befintligRad.getAntal();
        }

        int kanLaggasTill = produkt.getLagerAntal() - antalIKorgen;
        if (antal > kanLaggasTill) {
            return false;
        }

        if (befintligRad != null) {
            befintligRad.setAntal(antalIKorgen + antal);
        } else {
            VarukorgRad nyRad = new VarukorgRad(produkt, antal);
            rader.add(nyRad);
        }
        return true;
    }

    public ArrayList<VarukorgRad> getRader() {
        return rader;
    }

    public int getTotalSumma() {
        int summa = 0;
        for (int i = 0; i < rader.size(); i++) {
            VarukorgRad rad = rader.get(i);
            summa = summa + rad.getRadSumma();
        }
        return summa;
    }

    public int getAntalVaror() {
        int antalVaror = 0;
        for (int i = 0; i < rader.size(); i++) {
            VarukorgRad rad = rader.get(i);
            antalVaror = antalVaror + rad.getAntal();
        }
        return antalVaror;
    }

    public boolean isTom() {
        if (rader.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void laggTillRad(Produkt produkt, int antal) {
        VarukorgRad rad = new VarukorgRad(produkt, antal);
        rader.add(rad);
    }
}
