package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class ResponseHotelSingle {
    ArrayList<Hotel>  data;
    ArrayList<HotelCard> similler;

    public ArrayList<Hotel> getData() {
        return data;
    }

    public void setData(ArrayList<Hotel> data) {
        this.data = data;
    }

    public ArrayList<HotelCard> getSimiller() {
        return similler;
    }

    public void setSimiller(ArrayList<HotelCard> similler) {
        this.similler = similler;
    }
}
