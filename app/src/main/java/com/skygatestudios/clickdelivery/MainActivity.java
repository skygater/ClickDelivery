package com.skygatestudios.clickdelivery;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skygatestudios.clickdelivery.DataBase.DatabaseHelper;
import com.skygatestudios.clickdelivery.Java.City;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


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
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class  MainActivity extends AppCompatActivity {

    private List<City> mCity;
    private DatabaseHelper dbHelp;
    MaterialBetterSpinner niceSpinner;
    int cityIndex = 0;
    ProgressBar mProgressBar;
    String jason_strings;
    String json_result;
    TextView textView23;
    List<City> grad;
    String [] cityName;

    ArrayAdapter<String> adapter;
    JSONObject jsonObject;
    JSONArray jsonArray;

    Context ctx = this;


    String json_town;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.INVISIBLE);
        json_result = getIntent().getExtras().getString("json");
        grad = new ArrayList<>();
        if (json_result == null){
            textView23.setText("Nope JSON");
        }else {
            try {
                String a = "";
                jsonObject = new JSONObject(json_result);
                jsonArray = jsonObject.getJSONArray("server_responce");
                int i = 0;
                int id;
                String name;
                while (i < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    id = jo.getInt("id");
                    name = jo.getString("grad");
                    City city = new City(id, name);

                    grad.add(city);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        List<String> imegrad = new ArrayList<>();
            String g = "";
        for (City c : grad) {

            String a = c.getName();
            imegrad.add(a);
            g= g +", "+ c.getName();
        }


        niceSpinner = (MaterialBetterSpinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_dropdown_item_1line, imegrad);
        niceSpinner.setAdapter(adapter);



    }

    public void city (View view){
        cityIndex = niceSpinner.getPosition() + 1;
        String name = niceSpinner.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(cityIndex+"");

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
                if(json_result==null){
                    Toast.makeText(ctx,"LOL A DJE CE",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(ctx,cityIndex+"",Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(MainActivity.this,HomeActivity.class);
                    mainIntent.putExtra("json_town",json_town);

                    startActivity(mainIntent);

                }

                i[0]++;
                mProgressBar.setProgress(i[0]);



            }
        };
        mCountDownTimer.start();





    }


class BackgroundTask extends  AsyncTask<String,Void,String>{
    Context ctx;
        String json_url;
    BackgroundTask (Context ctx){

        this.ctx = ctx;
    }



    @Override
    protected void onPreExecute() {
        json_url = "http://clickdelivery.000webhostapp.com/apiJson/restorani_json.php";
    }
    @Override
    protected String doInBackground(String... params) {
        String grad = params[0];
        int pozicija = Integer.parseInt(grad);

        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("townId","UTF-8")+"="+URLEncoder.encode(grad,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((jason_strings = bufferedReader.readLine()) != null ){
                stringBuilder.append(jason_strings+"\n");
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

        json_town=result;
    }


}

    @Override
    public void onBackPressed() {
        finish();
       // moveTaskToBack(true);
    }
}
