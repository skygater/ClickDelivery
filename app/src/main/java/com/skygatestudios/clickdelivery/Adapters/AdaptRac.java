package com.skygatestudios.clickdelivery.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.Java.Order;
import com.skygatestudios.clickdelivery.R;

import java.util.List;

/**
 * Created by djordjekalezic on 27/01/2017.
 */

public class AdaptRac extends BaseAdapter {

    private Context ctx;
    private List<Order> orderi;
    public AdaptRac(Context ctx, List<Order> orderi) {
        this.ctx = ctx;
        this.orderi = orderi;
    }

    @Override
    public int getCount() {
        return orderi.size();
    }

    @Override
    public Object getItem(int position) {
        return orderi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view= View.inflate(ctx, R.layout.bill_item,null);

        TextView naziv, kolicina, cijena;
        naziv = (TextView) view.findViewById(R.id.hrana);
        kolicina = (TextView) view.findViewById(R.id.kol);
        cijena = (TextView) view.findViewById(R.id.cijena);

        naziv.setText(orderi.get(position).getNameFood());
        kolicina.setText(orderi.get(position).getQuantity());
        cijena.setText(orderi.get(position).getTotalPrice());


        return view;
    }
}
