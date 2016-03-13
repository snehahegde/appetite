package com.appetite;

/**
 * Created by Kavi on 3/7/16.
 */
public class ChefMenuDetails {

    String chefName;
    String ingredients;
    String quantity;

    public ChefMenuDetails(String ingredients, String quantity) {
        this.ingredients = ingredients;
        this.quantity = quantity;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
