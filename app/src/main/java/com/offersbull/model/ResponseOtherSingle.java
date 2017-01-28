package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseOtherSingle {
    ArrayList<Other>  data;
    ArrayList<OtherCard> similler;

    public ArrayList<Other> getData() {
        return data;
    }

    public void setData(ArrayList<Other> data) {
        this.data = data;
    }

    public ArrayList<OtherCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<OtherCard> similler) {
        this.similler = similler;
    }
}
