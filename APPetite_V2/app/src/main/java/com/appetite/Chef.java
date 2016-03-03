package com.appetite;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sneha on 2/15/2016.
 */
public class Chef {
    private String name;
    private String location;
    private ArrayList<String> reviews;
    private int rating;
    private HashMap<String, String> dishImageHashMap;

    public Chef(String name, String location, int rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        dishImageHashMap = new HashMap<>();
        //dishImageHashMap.put("Caesar Salad", "@drawable/caesarsalad");
    }

    public String getChefName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
}
