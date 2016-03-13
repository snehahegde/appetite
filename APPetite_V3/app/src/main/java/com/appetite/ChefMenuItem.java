package com.appetite;

/**
 * Created by Kavi on 3/2/16.
 */
public class ChefMenuItem {
    String itemName;
    String foodImg;
    String chefName;
    int quantity;

    public ChefMenuItem(){}
    public ChefMenuItem(String itemName, String foodImg, String chefName, int quantity) {
        this.itemName = itemName;
        this.foodImg = foodImg;
        this.quantity = quantity;
        this.chefName = chefName;
    }
    public ChefMenuItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
    public ChefMenuItem(int quantity){
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }




}
