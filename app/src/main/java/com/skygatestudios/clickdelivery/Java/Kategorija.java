package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class Kategorija {

    int id;
    String name;

    public Kategorija(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
