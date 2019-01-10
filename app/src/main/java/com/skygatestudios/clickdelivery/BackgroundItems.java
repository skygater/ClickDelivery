package com.skygatestudios.clickdelivery;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by djordjekalezic on 24/06/2017.
 */

public class BackgroundItems extends AsyncTask<String,Void, String> {

    Context ctx;
    String json_url;
    String json_String;

    String json_biznis;

    public String getJson_biznis() {
        return json_biznis;
    }

    public void setJson_biznis(String json_biznis) {
        this.json_biznis = json_biznis;
    }

    public BackgroundItems(Context ctx){
        this.ctx = ctx;
    }


    @Override
    protected void onPreExecute() {
       json_url = "http://clickdelivery.000webhostapp.com/food_json.php";
    }

    @Override
    protected String doInBackground(String... params) {
        String bizId= params[0];

        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("biz_id","UTF-8")+"="+URLEncoder.encode(bizId,"UTF-8");


            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((json_String = bufferedReader.readLine()) != null ){
                stringBuilder.append(json_String +"\n");
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
        setJson_biznis(result);
    }
}
