package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class Proizvod{

    int idRacun;
    int idItem;
    int kolicina;

    String nameItem;

    public Proizvod(int idRacun, int idItem, int kolicina, String nameItem) {
        this.idRacun = idRacun;
        this.idItem = idItem;
        this.kolicina = kolicina;
        this.nameItem = nameItem;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(int idRacun) {
        this.idRacun = idRacun;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }
}
