package se.labb1.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VarukorgTest {

    private Produkt mugg = new Produkt(1, "Kaffemugg", "Vit mugg", 79, 10);
    private Produkt penna = new Produkt(2, "Kulspetspenna", "Blå", 29, 10);
    private Produkt horlurar = new Produkt(3, "Hörlurar", "Trådlösa", 899, 0);

    @Test
    public void nyVarukorgArTom() {
        Varukorg varukorg = new Varukorg();

        assertTrue(varukorg.isTom());
        assertEquals(0, varukorg.getRader().size());
        assertEquals(0, varukorg.getTotalSumma());
        assertEquals(0, varukorg.getAntalVaror());
    }

    @Test
    public void laggTillEnVara() {
        Varukorg varukorg = new Varukorg();

        assertTrue(varukorg.laggTill(mugg, 2));

        assertFalse(varukorg.isTom());
        assertEquals(1, varukorg.getRader().size());
        assertEquals(2, varukorg.getRader().get(0).getAntal());
        assertEquals("Kaffemugg", varukorg.getRader().get(0).getProdukt().getNamn());
    }

    @Test
    public void sammaVaraTvaGangerOkarAntalet() {
        Varukorg varukorg = new Varukorg();

        varukorg.laggTill(mugg, 1);
        varukorg.laggTill(mugg, 3);

        assertEquals(1, varukorg.getRader().size());
        assertEquals(4, varukorg.getRader().get(0).getAntal());
    }

    @Test
    public void olikaVarorBlirOlikaRader() {
        Varukorg varukorg = new Varukorg();

        varukorg.laggTill(mugg, 1);
        varukorg.laggTill(penna, 1);

        assertEquals(2, varukorg.getRader().size());
    }

    @Test
    public void totalSummaOchAntalVaror() {
        Varukorg varukorg = new Varukorg();

        varukorg.laggTill(mugg, 2);
        varukorg.laggTill(penna, 3);

        assertEquals(158, varukorg.getRader().get(0).getRadSumma());
        assertEquals(158 + 87, varukorg.getTotalSumma());
        assertEquals(5, varukorg.getAntalVaror());
    }

    @Test
    public void kanLaggaTillAllaSomFinnsILager() {
        Varukorg varukorg = new Varukorg();

        assertTrue(varukorg.laggTill(mugg, 10));
        assertEquals(10, varukorg.getAntalVaror());
    }

    @Test
    public void kanInteLaggaTillFlerAnDetFinnsILager() {
        Varukorg varukorg = new Varukorg();

        assertFalse(varukorg.laggTill(mugg, 11));

        assertTrue(varukorg.isTom());
    }

    @Test
    public void detSomRedanFinnsIKorgenRaknasMed() {
        Varukorg varukorg = new Varukorg();

        varukorg.laggTill(mugg, 8);

        assertFalse(varukorg.laggTill(mugg, 3));
        assertEquals(8, varukorg.getRader().get(0).getAntal());

        assertTrue(varukorg.laggTill(mugg, 2));
        assertEquals(10, varukorg.getRader().get(0).getAntal());
    }

    @Test
    public void slutILagerGarInteAttLaggaTill() {
        Varukorg varukorg = new Varukorg();

        assertFalse(varukorg.laggTill(horlurar, 1));
        assertTrue(varukorg.isTom());
    }
}
