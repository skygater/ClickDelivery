package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 20/12/2016.
 */

public class City {

    int idgrad;
    String name;

    public City(int idgrad, String name) {
        this.idgrad = idgrad;
        this.name = name;
    }

    public int getIdgrad() {
        return idgrad;
    }

    public void setIdgrad(int idgrad) {
        this.idgrad = idgrad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
