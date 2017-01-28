package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseTutionSingle {
    ArrayList<Tution>  data;
    ArrayList<TutionCard> similler;

    public ArrayList<Tution> getData() {
        return data;
    }

    public void setData(ArrayList<Tution> data) {
        this.data = data;
    }

    public ArrayList<TutionCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<TutionCard> similler) {
        this.similler = similler;
    }
}
