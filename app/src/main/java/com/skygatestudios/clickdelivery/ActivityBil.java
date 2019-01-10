package com.skygatestudios.clickdelivery;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.skygatestudios.clickdelivery.Adapters.AdaptRac;
import com.skygatestudios.clickdelivery.Java.Dostava;
import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.Java.Order;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ActivityBil extends AppCompatActivity {

    List<Order> orders1;
    int restoran;
    ListView lw;
    AdaptRac adapter;
    int br = 0;
    TextView text;

    String json_por;

    List<Dostava> mDostava;
    String json_result;

    JSONObject jsonObject;
    JSONArray jsonArray;

    MaterialBetterSpinner niceSpinner;
    ArrayAdapter<String> adapter1;

    EditText ime,prezime,tel,adresa;
    String ime1,prezime1,tel1,adresa1;

    String json_strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bil);
        Intent intent = getIntent();
        orders1 = (ArrayList<Order>) intent.getSerializableExtra("lista");
        restoran = intent.getExtras().getInt("bizid");
        json_result = intent.getExtras().getString("json_dostava");
        mDostava = new ArrayList<>();
        if (json_result == null){
            Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show();
        }else {
            try {
                String a = "";
                jsonObject = new JSONObject(json_result);
                jsonArray = jsonObject.getJSONArray("server_responce");
                int i = 0;
                int idDostava;
                String name;
                int cijenaDost;

                while (i < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    idDostava = jo.getInt("iddost");
                    name = jo.getString("naziv");
                    cijenaDost = jo.getInt("cijena");

                    Dostava dostava = new Dostava(idDostava,name,cijenaDost);

                    mDostava.add(dostava);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        List<String> dosName = new ArrayList<>();
        String g = "";
        for (Dostava d : mDostava) {

            String lj = d.getNazivDost()+ " - "+ d.getCijena()+"eur";
            dosName.add(lj);
            g= g +", "+ d.getNazivDost();
        }

        niceSpinner = (MaterialBetterSpinner) findViewById(R.id.spinner23);

        adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, dosName);
        niceSpinner.setAdapter(adapter1);




        text = (TextView) findViewById(R.id.totalcijena);

        for (Order o: orders1) {
            int a = Integer.parseInt(o.getTotalPrice().toString());
            br = br+a;
        }

        text.setText(br+"eur");
        ime = (EditText) findViewById(R.id.ime);
        prezime = (EditText) findViewById(R.id.prezime);
        tel = (EditText) findViewById(R.id.tel);
        adresa = (EditText) findViewById(R.id.adresa);



    }
    public void listaJela(View view){

        Intent mainIntent = new Intent(ActivityBil.this,ItemSelected.class);
        mainIntent.putExtra("lista", (Serializable) orders1);
        startActivity(mainIntent);

    }

    public void poruci (View view){


            ime1 = ime.getText().toString();
            prezime1 = prezime.getText().toString();
            tel1 = tel.getText().toString();
            adresa1 = adresa.getText().toString();

        if(ime1.matches("")  || prezime1.matches("") || tel1.matches("") ||  adresa1.matches("")){
            Toast.makeText(this,"Molimo da unesete sva polja",Toast.LENGTH_LONG).show();

        }else {
            String spinner = niceSpinner.getText().toString();
            if (spinner.equalsIgnoreCase("Izaberi Dostavu")){
                Toast.makeText(this,"Izaberite dostavu",Toast.LENGTH_LONG).show();
            }else{
                String a =  mDostava.get(niceSpinner.getPosition()).getIdDostava()+"";
                int total = (mDostava.get(niceSpinner.getPosition()).getCijena() + br);

                int dostava = mDostava.get(niceSpinner.getPosition()).getCijena();


                Gson gson = new GsonBuilder().create();
                JsonArray myCustomArray = gson.toJsonTree(orders1).getAsJsonArray();
                String json_array = myCustomArray+"";



               BackgroundNarudzba backgroundNarudzba = new BackgroundNarudzba(this);
                backgroundNarudzba.execute(ime1,prezime1,tel1,adresa1+";","1",a,total+"",restoran+"",dostava+"",json_array);

                Toast.makeText(this,"Kupljeno",Toast.LENGTH_LONG).show();


                Intent mStartActivity = new Intent(ActivityBil.this, Finish.class);
                startActivity(mStartActivity);
            }

        }



    }

    class BackgroundNarudzba extends AsyncTask<String,Void,String> {
        Context ctx;

        BackgroundNarudzba (Context ctx){

            this.ctx = ctx;
        }



        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... params) {
            String ime2 = params[0];
            String prezime2 = params[1];
            String tel2 = params[2];
            String adresa2 = params[3];
            String placanje2 = params[4];

            String idDostava =params[5];
            String total = params[6];
            String biznis = params[7];
            String cijenaDostave = params[8];
            String json_nar = params[9];


            try {
                String json_url = "http://clickdelivery.000webhostapp.com/android_kupi.php";
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("ime","UTF-8")+"="+URLEncoder.encode(ime2,"UTF-8")+"&"+
                        URLEncoder.encode("prezime","UTF-8")+"="+URLEncoder.encode(prezime2,"UTF-8")+"&"+
                        URLEncoder.encode("tel","UTF-8")+"="+URLEncoder.encode(tel2,"UTF-8")+"&"+
                        URLEncoder.encode("adresa","UTF-8")+"="+URLEncoder.encode(adresa2,"UTF-8")+"&"+
                        URLEncoder.encode("placanje","UTF-8")+"="+URLEncoder.encode(placanje2,"UTF-8")+"&"+
                        URLEncoder.encode("idDostava","UTF-8")+"="+URLEncoder.encode(idDostava,"UTF-8")+"&"+
                        URLEncoder.encode("total","UTF-8")+"="+URLEncoder.encode(total,"UTF-8")+"&"+
                        URLEncoder.encode("biz","UTF-8")+"="+URLEncoder.encode(biznis,"UTF-8")+"&"+
                        URLEncoder.encode("cijenaDostave","UTF-8")+"="+URLEncoder.encode(cijenaDostave,"UTF-8")+"&"+
                        URLEncoder.encode("json_nar","UTF-8")+"="+URLEncoder.encode(json_nar,"UTF-8");



                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();


                return  "Proslo je ";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_por=result;
        }


    }





}
