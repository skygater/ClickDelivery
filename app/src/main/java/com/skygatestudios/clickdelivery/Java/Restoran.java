package com.skygatestudios.clickdelivery.Java;

/**
 * Created by djordjekalezic on 21/12/2016.
 */

public class Restoran {

    int id;
    String name;
    String tel;
    String email;
    String slika;
    String ulica;

    public Restoran(int id, String name, String tel, String email, String slika, String ulica) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.slika = slika;
        this.ulica = ulica;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }
    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }
}
