package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseTravalSingle {
    ArrayList<Travelling>  data;
    ArrayList<TravellingCard> similler;

    public ArrayList<Travelling> getData() {
        return data;
    }

    public void setData(ArrayList<Travelling> data) {
        this.data = data;
    }

    public ArrayList<TravellingCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<TravellingCard> similler) {
        this.similler = similler;
    }
}
