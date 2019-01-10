package com.skygatestudios.clickdelivery.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skygatestudios.clickdelivery.R;
import com.skygatestudios.clickdelivery.Restorant_Info;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class InfoFrag extends Fragment {

    TextView name;
    TextView adresa;
    TextView email;
    TextView tel;
    ImageView slika;
    View view;
    Context ctx;

    String naziv,  adress,  emails, telephone,  slika1;

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setSlika1(String slika1) {
        this.slika1 = slika1;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public Context getCtx() {
        return ctx;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_info,container,false);
        Bundle bundle =new Bundle();
        bundle = this.getArguments();


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) view.findViewById(R.id.fr_name);
        adresa = (TextView) view.findViewById(R.id.fr_adresa);
        email = (TextView) view.findViewById(R.id.fr_email);
        tel = (TextView) view.findViewById(R.id.fr_tel);
        slika = (ImageView) view.findViewById(R.id.fr_slika);

        name.setText(naziv);
        adresa.setText(adress);
        email.setText(emails);
        tel.setText(telephone);
        Picasso.with(ctx).load("http://clickdelivery.000webhostapp.com/"+slika1).into(slika);

    }



}
