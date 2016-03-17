package com.appetite;

/**
 * Created by Kavi on 3/7/16.
 */
public class ChefsEnrolledList {

    String chefName;
    String chefPic;
    String quantity;
    String ingredients;
    String foodImg;

    public ChefsEnrolledList(String chefName,String chefPic, String ingredients, String quantity ,String foodImg) {
        this.chefName = chefName;
        this.chefPic = chefPic;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.foodImg = foodImg;

    }
    public String getFoodImage() { return foodImg; }

    public void setFoodImage(String foodImg) { this.foodImg = foodImg; }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getChefPic() {
        return chefPic;
    }

    public void setChefPic(String chefPic) {
        this.chefPic = chefPic;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
