package com.appetite;

/**
 * Created by Kavi on 3/2/16.
 */
public class ChefMenuItem {
    String itemName;
    String foodImg;
    String chefName;
    String ingredients;



    String location;
    int quantity;

    public ChefMenuItem(){}

    public ChefMenuItem(String ingredients,int quantity,String imagePath){
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.foodImg = imagePath;

    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



}
