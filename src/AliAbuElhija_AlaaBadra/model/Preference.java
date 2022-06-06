package AliAbuElhija_AlaaBadra.model;

import java.io.Serializable;

public class Preference implements Serializable {

    private int startingHoer;
    private boolean fromHome;

    public Preference() {
        startingHoer = 8;
        fromHome = false;
    }

    public Preference(int startingHoer) {
        this();
        this.startingHoer = startingHoer;
    }

    public Preference(boolean fromHome) {
        this();
        this.fromHome = fromHome;
    }

    public int getStartingHoer() {
        return startingHoer;
    }

    public boolean isEarly(){
        return startingHoer < 8;
    }

    public boolean isLate(){
        return startingHoer > 8;
    }

    public boolean isStandard(){
        return startingHoer == 8 && !fromHome;
    }

    public boolean isFromHome(){
        return fromHome;
    }

    public String toString(){
        if(fromHome)
            return "From Home";
        return "From " + startingHoer + " To " + (startingHoer + 9);
    }
}
