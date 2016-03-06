package com.appetite;

import java.io.Serializable;

/**
 * Created by Sneha on 3/6/2016.
 */
public class ChefDishDetails implements Serializable {
    private String name;
    private String dishImagePath;
    private int quantity;
    private int price;

    public ChefDishDetails(String name,String dishImagePath, int quantity, int price) {
        this.name = name;
        this.dishImagePath = dishImagePath;
        this.quantity = quantity;
        this.price = price;
    }

}
