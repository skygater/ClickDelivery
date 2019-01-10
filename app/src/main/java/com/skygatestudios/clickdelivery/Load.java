package com.skygatestudios.clickdelivery;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skygatestudios.clickdelivery.Java.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Load extends AppCompatActivity {

    String jason_strings;
    String json_result;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        new BackgroundTask().execute();

        final ProgressBar mProgressBar;
        CountDownTimer mCountDownTimer;
        final int[] i = {0};

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(i[0]);


        mCountDownTimer=new CountDownTimer(4000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                i[0]++;
                mProgressBar.setProgress(i[0]);

            }

            @Override
            public void onFinish() {

                if(json_result==null){
                    Toast.makeText(ctx,"Resetujte app",Toast.LENGTH_SHORT).show();
                }else{
                    Intent myIntent = new Intent(Load.this, MainActivity.class);
                    myIntent.putExtra("json",json_result);
                    Load.this.startActivity(myIntent);

                }

                i[0]++;
                mProgressBar.setProgress(i[0]);



            }
        };
        mCountDownTimer.start();

    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        List<City> grad = new ArrayList<City>();

        public List<City> getGrad() {
            return grad;
        }

        public void setGrad(List<City> grad) {
            this.grad = grad;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://clickdelivery.000webhostapp.com/apiJson/city_json.php";
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
            json_result = result;

        }
    }

}
