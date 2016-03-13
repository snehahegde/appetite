package com.appetite;

import java.util.ArrayList;

/**
 * Created by Sneha on 2/16/2016.
 */
public class DishHelperClass {
    ArrayList<Dish> dishList = new ArrayList<>();

    private static DishHelperClass thisClass = null;

    public static DishHelperClass Create() {
        if(thisClass == null) {
            thisClass = new DishHelperClass();
        }

        return thisClass;
    }

    private DishHelperClass() {

    }

    public Dish getDish(String dishName) {
        Chef chef = new Chef("Emma", "Santa Clara", 4);
        Dish dish = new Dish("Caesar Salad", "/drawable/icon", "Lettuce, cheese, bread crumbs");
        dish.setChefsEnrolled(chef);
        return dish;
    }
}
