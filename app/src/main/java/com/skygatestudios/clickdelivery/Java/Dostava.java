package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 26/06/2017.
 */

public class Dostava {

    int idDostava;
    String nazivDost;
    int cijena;


    public Dostava(int idDostava, String nazivDost, int cijena) {
        this.idDostava = idDostava;
        this.nazivDost = nazivDost;
        this.cijena = cijena;
    }

    public int getIdDostava() {
        return idDostava;
    }

    public void setIdDostava(int idDostava) {
        this.idDostava = idDostava;
    }

    public String getNazivDost() {
        return nazivDost;
    }

    public void setNazivDost(String nazivDost) {
        this.nazivDost = nazivDost;
    }

    public int getCijena() {
        return cijena;
    }

    public void setCijena(int cijena) {
        this.cijena = cijena;
    }
}
