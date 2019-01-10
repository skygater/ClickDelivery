package com.skygatestudios.clickdelivery;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.skygatestudios.clickdelivery.Adapters.RecyclerAdapter;
import com.skygatestudios.clickdelivery.DataBase.DatabaseHelper;
import com.skygatestudios.clickdelivery.Java.City;
import com.skygatestudios.clickdelivery.Java.Restoran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recycleView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutMenager;
    String json_result ="";
    private List<Restoran> mRestoran;
    private DatabaseHelper dbHelp;

    JSONObject jsonObject;
    JSONArray jsonArray;

    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        json_result = getIntent().getExtras().getString("json_town");
        mRestoran = new ArrayList<>();
        if (json_result == null){
        }else {
            try {
                String a = "";
                jsonObject = new JSONObject(json_result);
                jsonArray = jsonObject.getJSONArray("responce");
                int i = 0;
                int id;
                String name;
                String tel;
                String email;
                String slika;
                String ulica;
                while (i < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    id = jo.getInt("idBiznis");
                    name = jo.getString("naziv");
                    tel = jo.getString("tel");
                    slika = jo.getString("slika");
                    ulica = jo.getString("ulica");
                    email = "example@lol.com";
                    Restoran restoran = new Restoran(id,name,tel,email,slika,ulica);

                    mRestoran.add(restoran);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        recycleView = (RecyclerView) findViewById(R.id.recycle_view);

        adapter = new RecyclerAdapter(mRestoran,this);
        recycleView.setHasFixedSize(true);
        layoutMenager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutMenager);
        recycleView.setAdapter(adapter);


    }


}