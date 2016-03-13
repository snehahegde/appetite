package com.appetite;

import java.util.ArrayList;

/**
 * Created by Sneha on 2/15/2016.
 */
public class Dish {
    private String dishName;
    private ArrayList<Chef> chefsEnrolled;
    private String imagePath;
    private String ingredients;

    public Dish(String dishName,String imagePath, String ingredients) {
        this.dishName  = dishName;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        chefsEnrolled = new ArrayList<>();
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return this.dishName;
    }

    public ArrayList<Chef> getChefsEnrolled() {

        return chefsEnrolled;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setChefsEnrolled(Chef chef) {
        chefsEnrolled.add(chef);
    }


}
