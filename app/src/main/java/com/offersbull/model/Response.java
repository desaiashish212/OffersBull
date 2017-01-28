package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/14/2017.
 */

public class Response {
    ArrayList<SingleItemModel> latest ;
    ArrayList<SingleItemModel> polular ;
    ArrayList<SingleItemModel> more ;

    public ArrayList<SingleItemModel> getLatest() {
        return latest;
    }

    public void setLatest(ArrayList<SingleItemModel> latest) {
        this.latest = latest;
    }

    public ArrayList<SingleItemModel> getPolular() {
        return polular;
    }

    public void setPolular(ArrayList<SingleItemModel> polular) {
        this.polular = polular;
    }

    public ArrayList<SingleItemModel> getMore() {
        return more;
    }

    public void setMore(ArrayList<SingleItemModel> more) {
        this.more = more;
    }
}
