package se.labb1.bo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnvandareTest {

    @Test
    public void adminArAdmin() {
        Anvandare admin = new Anvandare(1, "admin", Roll.ADMIN);

        assertTrue(admin.isAdmin());
    }

    @Test
    public void kundOchLagerArInteAdmin() {
        Anvandare kund = new Anvandare(2, "kund", Roll.KUND);
        Anvandare lager = new Anvandare(3, "lager", Roll.LAGER);

        assertFalse(kund.isAdmin());
        assertFalse(lager.isAdmin());
    }
}
