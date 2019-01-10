package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class Item {
    int idItem;
    String name;
    int gram;
    String price;
    String description;
    public int quantity;
    String kat_name;

    public Item(int idItem, String name, int gram, String price, String kat_name) {
        this.idItem = idItem;
        this.name = name;
        this.gram = gram;
        this.price = price;
        this.kat_name = kat_name;
    }

    public void setKat_name(String kat_name) {
        this.kat_name = kat_name;
    }

    public String getKat_name() {
        return kat_name;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
