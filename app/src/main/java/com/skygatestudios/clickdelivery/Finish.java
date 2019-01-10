package com.skygatestudios.clickdelivery;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.skygatestudios.clickdelivery.Java.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Finish extends AppCompatActivity {

    String jason_strings;
    String json_result;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);


        final ProgressBar mProgressBar;
        CountDownTimer mCountDownTimer;
        final int[] i = {0};

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar32);
        mProgressBar.setProgress(i[0]);


        mCountDownTimer=new CountDownTimer(2000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                i[0]++;
                mProgressBar.setProgress(i[0]);

            }

            @Override
            public void onFinish() {

                Intent mStartActivity = new Intent(Finish.this, Load.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Finish.this, mPendingIntentId, mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) Finish.this.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);


                i[0]++;
                mProgressBar.setProgress(i[0]);



            }
        };
        mCountDownTimer.start();

    }


}
