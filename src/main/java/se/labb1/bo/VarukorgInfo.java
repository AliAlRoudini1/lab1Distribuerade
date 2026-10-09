package se.labb1.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class VarukorgInfo implements Serializable {

    private final ArrayList<VarukorgRadInfo> rader;
    private final int totalSumma;
    private final int antalVaror;
    private final boolean tom;

    public VarukorgInfo(ArrayList<VarukorgRadInfo> rader, int totalSumma, int antalVaror, boolean tom) {
        this.rader = new ArrayList<VarukorgRadInfo>(rader);
        this.totalSumma = totalSumma;
        this.antalVaror = antalVaror;
        this.tom = tom;
    }

    public ArrayList<VarukorgRadInfo> getRader() {
        ArrayList<VarukorgRadInfo> kopia = new ArrayList<VarukorgRadInfo>(rader);
        return kopia;
    }

    public int getTotalSumma() {
        return totalSumma;
    }

    public int getAntalVaror() {
        return antalVaror;
    }

    public boolean isTom() {
        return tom;
    }
}
