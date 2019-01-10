package com.skygatestudios.clickdelivery.DataBase;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.skygatestudios.clickdelivery.Java.City;
import com.skygatestudios.clickdelivery.Java.Item;
import com.skygatestudios.clickdelivery.Java.Kategorija;
import com.skygatestudios.clickdelivery.Java.Restoran;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djordjekalezic on 20/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public  static  final  String DBNAME = "clickdb.db";
    public static final String DBLOCATION = "/data/data/com.skygatestudios.clickdelivery/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase(){

        String dbPath = mContext.getDatabasePath(DBNAME).getPath();

        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    public List<City> getCityName(){
        City city = null;
        List<City> cityList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("Select * from grad", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            city = new City(cursor.getInt(0),cursor.getString(1));
            cityList.add(city);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return cityList;

    }

    public List <Restoran> getRestoran (int id){
        Restoran restoran = null;
        List<Restoran> restoranList = new ArrayList<Restoran>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select b.idbiz, b.name, b.tel, b.email, b.slika, l.namestr  from biznis b, lokacija l, grad g where g.idgrad = ? and l.idgrad = g.idgrad and b.idloc = l.idloc", new String[]{String.valueOf(id)});
        cursor.moveToNext();
        while (!cursor.isAfterLast()){
            restoran = new Restoran(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            restoranList.add(restoran);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return restoranList;
    }

    public List<Kategorija> getKategorija(int id){

        Kategorija kategorija = null;
        List <Kategorija> kategorijaList = new ArrayList<Kategorija>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select k.idkat, k.name from Kategorija k, Biznis b, Meni m where  b.idbiz = ? and m.idbiz = b.idbiz and k.idmeni = m.idmeni ",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            kategorija = new Kategorija(cursor.getInt(0),cursor.getString(1));
            kategorijaList.add(kategorija);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return kategorijaList;
    }

    public List<Item> getItems (int id){
        Item item = null;
        List<Item> itemList = new ArrayList<Item>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select i.iditem, i.name, i.gram,i.price,i.description from Kategorija k, Biznis b, Meni m,Item i where  b.idbiz = ? and m.idbiz = b.idbiz and k.idmeni = m.idmeni and i.idkat=k.idkat",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            item = new Item(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            item.quantity= 0;
            itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }

    public List<Item> getItemsbyKat (int id, String name){
        Item item = null;
        List<Item> itemList = new ArrayList<Item>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select i.iditem, i.name, i.gram,i.price,i.description from Kategorija k, Biznis b, Meni m,Item i where  b.idbiz = ? and m.idbiz = b.idbiz and k.idmeni = m.idmeni and k.name like '"+name+"' and i.idkat=k.idkat",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            item = new Item(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4));
            itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }
}
