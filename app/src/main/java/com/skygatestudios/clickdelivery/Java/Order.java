package com.skygatestudios.clickdelivery.Java;

import java.io.Serializable;

/**
 * Created by djordjekalezic on 27/01/2017.
 */

public class Order implements Serializable {
    private int idItem;
    private String nameFood;
    private String quantity;
    private String totalPrice;

    public Order(int idItem,String nameFood, String quantity, String totalPrice) {
        this.idItem = idItem;
        this.nameFood = nameFood;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
