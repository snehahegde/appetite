package com.appetite;

/**
 * Created by Kavi on 2/22/16.
 */
public class Menu {
    private String itemPrice;
    private String itemImg;
    private String itemName;
    private String itemCuisine;

    public Menu(){}
    public Menu(String itemName,String itemPrice,String itemImg,String itemCuisine) {
        this.itemPrice = itemPrice;
        this.itemImg = itemImg;
        this.itemName = itemName;
        this.itemCuisine = itemCuisine;
    }


    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemImg() {
        return itemImg;
    }

    public String getItemName() {
        return itemName;
    }
    public String getItemCuisine() {
        return itemCuisine;
    }


}
