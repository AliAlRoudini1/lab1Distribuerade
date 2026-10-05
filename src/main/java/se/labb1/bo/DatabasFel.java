package se.labb1.bo;

public class DatabasFel extends Exception {

    public DatabasFel(String meddelande, Throwable orsak) {
        super(meddelande, orsak);
    }
}
