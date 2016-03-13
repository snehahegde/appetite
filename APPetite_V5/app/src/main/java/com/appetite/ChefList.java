package com.appetite;

/**
 * Created by Kavi on 3/3/16.
 */
public class ChefList {

    String menuName;
    int quantity;
    String menuImg;
    String ingredients;

    public ChefList(){}

    public ChefList(String menuName,int quantity,String ingredients){

        this.menuName = menuName;
        this.quantity = quantity;
        this.ingredients = ingredients;

    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
