package com.offersbull.model;

import java.util.ArrayList;

/**
 * Created by AD on 1/16/2017.
 */

public class SectionDataModel {


    private String title;
    private ArrayList<SingleItemModel> posts;


    public SectionDataModel() {

    }

    public SectionDataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
        this.title = headerTitle;
        this.posts = allItemsInSection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SingleItemModel> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<SingleItemModel> posts) {
        this.posts = posts;
    }

}