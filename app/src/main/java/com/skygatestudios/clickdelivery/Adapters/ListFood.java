package com.skygatestudios.clickdelivery.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.Java.Order;
import com.skygatestudios.clickdelivery.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by djordjekalezic on 22/12/2016.
 */

public class ListFood extends BaseSwipeAdapter {

    private Context ctx;
    private List<Item> items;
    private List <Order> orders;

    View view;

    public ListFood(Context ctx, List<Item> items,List<Order> order) {
        this.ctx = ctx;
        this.items = items;
        this.orders =order;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    @Override
    public View generateView(final int position, ViewGroup parent) {
        view= View.inflate(ctx, R.layout.item_layout,null);
        final TextView name, gram, price, decript;
        final  TextView kolicina = (TextView) view.findViewById(R.id.kolicina);
        name = (TextView) view.findViewById(R.id.item_name);
        gram = (TextView) view.findViewById(R.id.item_gram);
        price = (TextView) view.findViewById(R.id.item_cijena);



        name.setText(items.get(position).getName());
        gram.setText(items.get(position).getGram() +"");
        price.setText(items.get(position).getPrice()+"");
        items.get(position).quantity=1;
         kolicina.setText(items.get(position).quantity+"");


        SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.minus));
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.plus));
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.add));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(ctx, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int a = Integer.parseInt(kolicina.getText().toString());
                if (a <=1){
                    int c = 1;
                    items.get(position).quantity=1;
                    kolicina.setText(c+"");
                }else {
                    kolicina.setText(--items.get(position).quantity + "");
                }

            }
        });
        view.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kolicina.setText(++items.get(position).quantity+"");

            }
        });

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Unjeli ste jelo "+items.get(position).getName(), Toast.LENGTH_SHORT).show();

                int idItem = items.get(position).getIdItem();
                String name = items.get(position).getName();
                String cijena = items.get(position).getPrice();
                String quantity = items.get(position).quantity+"";
                    int che = 0;

                    Order o = new Order(idItem,name,quantity,cijena);
                    orders.add(o);






            }
        });

        view.setTag(items.get(position).getIdItem());
        return view;

    }
    @Override
    public void fillValues(int position, View convertView) {

    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getIdItem();
    }



}
