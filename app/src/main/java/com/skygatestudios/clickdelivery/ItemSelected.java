package com.skygatestudios.clickdelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.skygatestudios.clickdelivery.Adapters.AdaptRac;
import com.skygatestudios.clickdelivery.Java.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemSelected extends AppCompatActivity {
    List<Order> orders1;
    int restoran;
    ListView lw;
    AdaptRac adapter;
    int br = 0;
    TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        lw = (ListView) findViewById(R.id.list_order);
        Intent intent = getIntent();
        orders1 = (ArrayList<Order>) intent.getSerializableExtra("lista");


        adapter = new AdaptRac(this,orders1);
        lw.setAdapter(adapter);


        text = (TextView) findViewById(R.id.totalcijena1);

        for (Order o: orders1) {
            int a = Integer.parseInt(o.getTotalPrice().toString());
            br = br+a;
        }

        text.setText(br+"eur");




    }

    public void nazad(View view){

        ItemSelected.this.onBackPressed();

    }
}
