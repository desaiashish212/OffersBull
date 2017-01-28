package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseRealSingle {
    ArrayList<RealState>  data;
    ArrayList<RealStateCard> similler;

    public ArrayList<RealState> getData() {
        return data;
    }

    public void setData(ArrayList<RealState> data) {
        this.data = data;
    }

    public ArrayList<RealStateCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<RealStateCard> similler) {
        this.similler = similler;
    }
}
