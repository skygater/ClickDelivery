package com.skygatestudios.clickdelivery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skygatestudios.clickdelivery.BackgroundItems;
import com.skygatestudios.clickdelivery.HomeActivity;
import com.skygatestudios.clickdelivery.Java.Restoran;
import com.skygatestudios.clickdelivery.MainActivity;
import com.skygatestudios.clickdelivery.R;
import com.skygatestudios.clickdelivery.Restorant_Info;
import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by djordjekalezic on 21/12/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{

    private List <Restoran> restorani = new ArrayList<Restoran>();
    private  Context con;



    private Bitmap bitmap;


    public RecyclerAdapter(List<Restoran> restorani, Context con){
        this.restorani = restorani;
        this.con = con;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,con,restorani);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Restoran restoran = restorani.get(position);
        //int id = con.getResources().getIdentifier(restoran.getSlika(), "drawable", con.getPackageName());


        Picasso.with(con).load("http://clickdelivery.000webhostapp.com/"+restoran.getSlika()).into(holder.image);

        holder.name.setText(restoran.getName());
        holder.tel.setText("tel: "+restoran.getTel());
        holder.email.setText(restoran.getEmail());
        holder.location.setText(restoran.getUlica());
    }

    @Override
    public int getItemCount() {
        return restorani.size();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            return myBitmap;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public static class  RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView name,tel,email,location;
        TextView json;
        List<Restoran> restorani = new ArrayList<Restoran>();
        Context ctx;
        ProgressBar mProgressBar;

        public RecyclerViewHolder(View view, Context ctx,List<Restoran> restorani){
            super(view);

            this.restorani = restorani;
            this.ctx = ctx;


            view.setOnClickListener(this);
            image = (ImageView) view.findViewById(R.id.res_slika);
            name = (TextView) view.findViewById(R.id.res_name);
            tel = (TextView) view.findViewById(R.id.res_tel);
            email = (TextView) view.findViewById(R.id.res_email);
            location = (TextView) view.findViewById(R.id.res_location);
            mProgressBar=(ProgressBar) view.findViewById(R.id.progressBar2);
        }
        String json_biznis;
        String json_kat;

        @Override
        public void onClick(View v) {
            final Context con = ctx;
            int position = getAdapterPosition();
            final Restoran restoran = this.restorani.get(position);
            BackgroundItems backgroundTask = new BackgroundItems(ctx);
            backgroundTask.execute(restoran.getId()+"");
            BackgroundKat backgroundKat = new BackgroundKat(ctx);
            backgroundKat.execute(restoran.getId()+"");

            CountDownTimer mCountDownTimer;

            final int[] i = {0};
            mProgressBar.setProgress(i[0]);
            mProgressBar.setVisibility(View.VISIBLE);

            mCountDownTimer=new CountDownTimer(5000,1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                    i[0]++;
                    mProgressBar.setProgress(i[0]);

                }

                @Override
                public void onFinish() {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    ////////Toast.makeText(ctx,json_kat,Toast.LENGTH_LONG).show();
                    // json.setText(backgroundItems.getJson_biznis());
                    Intent mainIntent = new Intent(con,Restorant_Info.class);
                    mainIntent.putExtra("json_town",json_biznis);
                    mainIntent.putExtra("json_kat",json_kat);
                    mainIntent.putExtra("idBiz",restoran.getId());
                    mainIntent.putExtra("slika",restoran.getSlika()) ;
                    mainIntent.putExtra("naziv",restoran.getName());
                    mainIntent.putExtra("tel", restoran.getTel());
                    mainIntent.putExtra("ulica",restoran.getUlica());
                    mainIntent.putExtra("email",restoran.getEmail());

                    con.startActivity(mainIntent);

                    i[0]++;
                    mProgressBar.setProgress(i[0]);
                }
            };
            mCountDownTimer.start();


        }
        String json_String;


        String bizId;

        public  class BackgroundItems extends AsyncTask<String,Void, String> {

            Context ctx;
            String json_url;


            public BackgroundItems(Context ctx){

                this.ctx = ctx;
            }


            @Override
            protected void onPreExecute() {
                json_url = "http://clickdelivery.000webhostapp.com/food_json.php";
            }

            @Override
            protected String doInBackground(String... params) {

                bizId= params[0];
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
                json_biznis = result;

            }
        }

        public  class BackgroundKat extends AsyncTask<String,Void, String> {

                Context ctx;
                String json_url;


                public BackgroundKat(Context ctx){

                    this.ctx = ctx;
                }


                @Override
                protected void onPreExecute() {
                    json_url = "http://clickdelivery.000webhostapp.com/kat_json.php";
                }

                @Override
                protected String doInBackground(String... params) {

                    bizId= params[0];
                    try {
                        URL url = new URL(json_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                        String data = URLEncoder.encode("bizi","UTF-8")+"="+URLEncoder.encode(bizId,"UTF-8");


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
                    json_kat = result;

                }
            }

    }



}
