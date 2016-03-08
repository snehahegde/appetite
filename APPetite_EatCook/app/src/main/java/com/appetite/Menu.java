package com.appetite;

/**
 * Created by Kavi on 2/22/16.
 */
public class Menu {
    private String itemPrice;
    private String itemImg;
    private String itemName;

    public Menu(){}
    public Menu(String itemName,String itemPrice,String itemImg) {
        this.itemPrice = itemPrice;
        this.itemImg = itemImg;
        this.itemName = itemName;
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


}
