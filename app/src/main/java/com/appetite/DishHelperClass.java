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
        Chef chef = new Chef("Emma", "Santa Clara", 4, 37.3492, -121.9381);
        Chef chef1 = new Chef("Anna", "Sunnyvale", 4, 37.376733, -122.063392);
        Dish dish = new Dish("Caesar Salad", "/drawable/icon", "Lettuce, cheese, bread crumbs");
        dish.setChefsEnrolled(chef);
        dish.setChefsEnrolled(chef1);
        chef.addNewDish(new ChefDishDetails("Caesar Salad", "/drawable/icon", 5, 10));
        chef1.addNewDish(new ChefDishDetails("Caesar Salad","/drawable/icon",6,9));

        return dish;
    }
}
