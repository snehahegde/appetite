package com.appetite;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Sneha on 2/15/2016.
 */

public class Chef implements Serializable {

    private String name;
    private String address;
    private ArrayList<String> reviews;
    private int rating;
    //private HashMap<String, String> dishImageHashMap;
    private ArrayList<ChefDishDetails> dishDetails;
    private double latitude;
    private double longitude;

    public Chef(String name, String location, int rating, double latitude, double longitude) {
        this.name = name;
        this.address = location;
        this.rating = rating;
        //dishImageHashMap = new HashMap<>();
        dishDetails = new ArrayList<ChefDishDetails>();

        this.latitude = latitude;
        this.longitude = longitude;
        //dishImageHashMap.put("Caesar Salad", "@drawable/caesarsalad");
    }

    public String getChefName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {

        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void addNewDish(ChefDishDetails dish) {
        dishDetails.add(dish);
    }

    static public class DistanceComparator implements Comparator<Chef> {
        private double mLat;
        private double mLong;
        public DistanceComparator(double lat, double longitude) {
            mLat  = lat;
            mLong = longitude;
        }
        @Override
        public int compare(Chef lhs, Chef rhs) {
            double distance1 = Math.sqrt((Math.pow(mLat - lhs.getLatitude(), 2) + (Math.pow(mLong - lhs.getLongitude(), 2))));
            double distance2 = Math.sqrt((Math.pow(mLat - rhs.getLatitude(), 2) + (Math.pow(mLong - rhs.getLongitude(), 2))));

            Log.d("distance1", String.valueOf(distance1 * 69));
            Log.d("distance2", String.valueOf(distance2 * 69));

            if(distance1 < distance2) {
                return -1;
            } else if(distance1 > distance2) {
                return 1;
            } else {
                return 0;
            }


        }

     }

}
