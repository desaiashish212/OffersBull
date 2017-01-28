package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseAutomobileSingle {
    ArrayList<Automobile>  data;
    ArrayList<AutomobileCard> similler;

    public ArrayList<Automobile> getData() {
        return data;
    }

    public void setData(ArrayList<Automobile> data) {
        this.data = data;
    }

    public ArrayList<AutomobileCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<AutomobileCard> similler) {
        this.similler = similler;
    }
}
