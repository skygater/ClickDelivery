package com.skygatestudios.clickdelivery;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.skygatestudios.clickdelivery.Adapters.ListFood;
import com.skygatestudios.clickdelivery.Adapters.RecycleFood;
import com.skygatestudios.clickdelivery.Adapters.RecyclerAdapter;
import com.skygatestudios.clickdelivery.DataBase.DatabaseHelper;
import com.skygatestudios.clickdelivery.Fragments.InfoFrag;
import com.skygatestudios.clickdelivery.Java.City;
import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.Java.Kategorija;
import com.skygatestudios.clickdelivery.Java.Order;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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

public class Restorant_Info extends AppCompatActivity {
    String name;

    int idRest = 0;

    private SpaceNavigationView spaceNavigationView;
    FragmentManager fm;
    FragmentTransaction ft;
    InfoFrag f1;

    //Spinner
    private List<Item>mItem;
    private List<Kategorija> mKat;

   MaterialBetterSpinner niceSpinner;


    private ListView lw;
    private ListFood adapter;
    private List <Order> orders;


    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONObject jsonObject1;
    JSONArray jsonArray1;
    String json_biz ;
    String json_kat;
    ArrayAdapter<String> adapter1;
    private Context mContext = this;

    int idBiz;
     String slika,naziv,tel,ulica,email;

    String json_String;

    String json_dostava;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restorant_info);
        orders = new ArrayList<>();
        lw  = (ListView) findViewById(R.id.lista);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar18);


        json_biz = getIntent().getExtras().getString("json_town");
        json_kat = getIntent().getExtras().getString("json_kat");

        idBiz = getIntent().getExtras().getInt("idBiz");

        slika = getIntent().getExtras().getString("slika");
        naziv = getIntent().getExtras().getString("naziv");
        tel = getIntent().getExtras().getString("tel");
        ulica= getIntent().getExtras().getString("ulica");
        email = getIntent().getExtras().getString("email");


        mItem=new ArrayList<>();
        if (json_biz == null){
            Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show();
        }else {
            try {
                String a = "";
                jsonObject = new JSONObject(json_biz);
                jsonArray = jsonObject.getJSONArray("server_responce");
                int i = 0;
                int idItem;
                String name;
                int gram;
                String price;
                String description;
                String kat_name;

                while (i < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    kat_name = jo.getString("katname");
                    idItem = jo.getInt("idItem");
                    name = jo.getString("naziv");
                    gram= jo.getInt("gramaza");
                    price=jo.getString("cijena");
                    description="Neki opis";

                    Item item = new Item(idItem,name,gram,price,kat_name);
                    mItem.add(item);
                    i++;
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mKat= new ArrayList<>();
        if (json_kat== null){
            Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show();
        }else {
            try {
                String a = "";
                jsonObject1 = new JSONObject(json_kat);
                jsonArray1 = jsonObject1.getJSONArray("server_responce");
                int i = 0;
                int idkat;
                String name;


                while (i < jsonArray1.length()) {
                    JSONObject jo = jsonArray1.getJSONObject(i);
                    idkat= jo.getInt("idkat");
                    name = jo.getString("nazivkat");


                   Kategorija kategorija = new Kategorija(idkat,name);
                    mKat.add(kategorija);
                    i++;
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        List<String> katName = new ArrayList<>();
        String g = "";
        for (Kategorija k : mKat) {

            String a = k.getName();
            katName.add(a);
            g= g +", "+ k.getName();
        }




        niceSpinner = (MaterialBetterSpinner) findViewById(R.id.spinner1);

         adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, katName);
        niceSpinner.setAdapter(adapter1);


        //Toast.makeText(this, mItem.get(1).getName(), Toast.LENGTH_SHORT).show();

        adapter =new ListFood(this,mItem, orders);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout)(lw.getChildAt(position - lw.getFirstVisiblePosition()))).open(true);
            }
        });
        lw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        lw.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext , "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        lw.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Toast.makeText(mContext , "onScrollStateChanged", Toast.LENGTH_SHORT).show();
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });




        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("INFO", R.drawable.account));
        spaceNavigationView.addSpaceItem(new SpaceItem("KORPA", R.drawable.korpa));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        final Context con = this;

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                if(itemIndex == 0){
                    f1 = new InfoFrag();
                    f1.setCtx(con);
                    f1.setNaziv(naziv);
                    f1.setAdress(ulica);
                    f1.setTelephone(tel);
                    f1.setEmails(email);
                    f1.setSlika1(slika);
                    fm = getFragmentManager();
                     ft = fm.beginTransaction();
                    ft.add(R.id.main_centre,f1);

                    //ft.commit();
                    Toast.makeText(Restorant_Info.this, "KLIKnuto dugme 0", Toast.LENGTH_SHORT).show();
                }else {

                    if(orders.isEmpty()){
                        Toast.makeText(Restorant_Info.this, "Izaberite Jela",Toast.LENGTH_SHORT).show();
                    }else{

                        BackgroundTask backgroundTask = new BackgroundTask(con);
                        backgroundTask.execute(idBiz+"");


                        CountDownTimer mCountDownTimer;

                        final int[] i = {0};

                        mProgressBar.setProgress(i[0]);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mCountDownTimer=new CountDownTimer(2000,1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                                i[0]++;
                                mProgressBar.setProgress(i[0]);

                            }

                            @Override
                            public void onFinish() {
                                mProgressBar.setVisibility(View.INVISIBLE);

                                Intent mainIntent = new Intent(Restorant_Info.this,ActivityBil.class);
                                mainIntent.putExtra("lista", (Serializable) orders);
                                mainIntent.putExtra("bizid", idBiz);
                                mainIntent.putExtra("json_dostava",json_dostava);
                                startActivity(mainIntent);

                                i[0]++;
                                mProgressBar.setProgress(i[0]);



                            }
                        };
                        mCountDownTimer.start();





                    }

                }

            }
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                spaceNavigationView.shouldShowFullBadgeText(true);
                Intent intent = getIntent();
                finish();
                startActivity(intent);



            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
                if(itemIndex == 0){
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    f1 = new InfoFrag();
                    f1.setCtx(con);
                    f1.setNaziv(naziv);
                    f1.setAdress(ulica);
                    f1.setTelephone(tel);
                    f1.setEmails(email);
                    f1.setSlika1(slika);
                    ft.add(R.id.main_centre,f1);
                    ft.commit();
                    //Toast.makeText(Restorant_Info.this, "KLIKnuto dugme 0", Toast.LENGTH_SHORT).show();
                }else {
                    if(orders.isEmpty()){
                        Toast.makeText(Restorant_Info.this, "Izaberite Jela", Toast.LENGTH_SHORT).show();
                    }else{
                        BackgroundTask backgroundTask = new BackgroundTask(con);
                        backgroundTask.execute(idBiz+"");


                        CountDownTimer mCountDownTimer;

                        final int[] i = {0};

                        mProgressBar.setProgress(i[0]);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mCountDownTimer=new CountDownTimer(2000,1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                                i[0]++;
                                mProgressBar.setProgress(i[0]);

                            }

                            @Override
                            public void onFinish() {
                                mProgressBar.setVisibility(View.INVISIBLE);

                                Intent mainIntent = new Intent(Restorant_Info.this,ActivityBil.class);
                                mainIntent.putExtra("lista", (Serializable) orders);
                                mainIntent.putExtra("bizid", idBiz);
                                mainIntent.putExtra("json_dostava",json_dostava);
                                startActivity(mainIntent);

                                i[0]++;
                                mProgressBar.setProgress(i[0]);



                            }
                        };
                        mCountDownTimer.start();


                    }


                }
            }
        });
    }

    public void kategorija (View view){
        String a = niceSpinner.getText().toString();
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
        if (a.equalsIgnoreCase("Izaberi Kategoriju")){
            Toast.makeText(this,"Izaberite Kategoriju",Toast.LENGTH_LONG).show();
        }else {

            List<Item> nItems = new ArrayList<>();
            for (Item i: mItem ) {
                if(a.equalsIgnoreCase(i.getKat_name())){
                    nItems.add(i);
                }
            }

            lw  = (ListView) findViewById(R.id.lista);
            adapter =new ListFood(this,nItems,orders);
            lw.setAdapter(adapter);
        }



    }




    class BackgroundTask extends AsyncTask<String,Void,String> {
        Context ctx;
        String json_url;
        BackgroundTask (Context ctx){

            this.ctx = ctx;
        }



        @Override
        protected void onPreExecute() {
            json_url = "http://clickdelivery.000webhostapp.com/dostava_json.php";
        }
        @Override
        protected String doInBackground(String... params) {
            String biznis = params[0];


            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("biz","UTF-8")+"="+URLEncoder.encode(biznis,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_String = bufferedReader.readLine()) != null ){
                    stringBuilder.append(json_String+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return  stringBuilder.toString().trim();


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

            json_dostava=result;
        }


    }



}
