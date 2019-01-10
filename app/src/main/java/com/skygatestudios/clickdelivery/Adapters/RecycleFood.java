package com.skygatestudios.clickdelivery.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class RecycleFood extends RecyclerView.Adapter<RecycleFood.RecyclerViewHolder> {

    List<Item> items = new ArrayList<Item>();

    public RecycleFood(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Item item = items.get(position);
        holder.name.setText(item.getName());
        holder.gram.setText(item.getGram());
        holder.price.setText(item.getPrice());
        holder.decript.setText(item.getDescription());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class  RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView name, gram, price, decript;

        public RecyclerViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            gram = (TextView) view.findViewById(R.id.item_gram);
            price = (TextView) view.findViewById(R.id.item_cijena);


        }
    }
}
