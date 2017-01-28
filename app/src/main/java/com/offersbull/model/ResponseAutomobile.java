package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseAutomobile {
    ArrayList<AutomobileCard> automobilefeed ;

    public ArrayList<AutomobileCard> getAutomobileCards() {
        return automobilefeed;
    }

    public void setAutomobileCards(ArrayList<AutomobileCard> automobileCards) {
        this.automobilefeed = automobileCards;
    }
}
