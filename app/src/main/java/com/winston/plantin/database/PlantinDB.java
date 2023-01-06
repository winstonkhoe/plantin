package com.winston.plantin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.winston.plantin.model.Favorite;
import com.winston.plantin.model.PlantShop;
import com.winston.plantin.model.User;
import com.winston.plantin.utility.Session;

import java.util.ArrayList;

public class PlantinDB {
    private DBHelper dbHelper;

    public PlantinDB(Context ctx){
        dbHelper = new DBHelper(ctx);
    }

    /* ----------------------------------------------------- USER ------------------------------------------------------------- */

    public void insertUser(User user){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("INSERT INTO user VALUES (null, ?, ?, ?)");
        statement.bindString(1, user.getEmail());
        statement.bindString(2, user.getPassword());
        statement.bindString(3, user.getPhoneNumber());
        statement.executeInsert();
        db.close();
    }

    public boolean getUserByEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM user WHERE email = '%s' LIMIT 1", email);
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() == 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public User getUserByEmailAndPassword(String email, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s' LIMIT 1", email, password);
        Cursor c = db.rawQuery(query, null);
        if(c.moveToNext()){
            User user = new User();
            user.setID(c.getInt(0));
            user.setEmail(c.getString(1));
            user.setPassword(c.getString(2));
            user.setPhoneNumber(c.getString(3));
            return user;
        }
        return null;
    }

    /* ----------------------------------------------------- PlantShop ------------------------------------------------------------- */

    public void insertPlantShop(PlantShop shop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("INSERT INTO PlantShop VALUES (null, ?, ?, ?, ?, ?)");
        statement.bindString(1, shop.getName());
        statement.bindString(2, shop.getImage());
        statement.bindString(3, shop.getLocation());
        statement.bindDouble(4, shop.getLatitude());
        statement.bindDouble(5, shop.getLongitude());
        statement.executeInsert();
        db.close();
    }
    public boolean checkPlantShopData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM PlantShop";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() == 0){
            return false;
        }
        return true;
    }
    public ArrayList<PlantShop> getAllPlantShop(){
        ArrayList<PlantShop> PlantShops = new ArrayList<PlantShop>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM PlantShop";
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()){
            PlantShop temp = new PlantShop();
            temp.setShopID(c.getInt(0));
            temp.setName(c.getString(1));
            temp.setImage(c.getString(2));
            temp.setLocation(c.getString(3));
            temp.setLatitude(c.getDouble(4));
            temp.setLongitude(c.getDouble(5));
            PlantShops.add(temp);
        }
        return PlantShops;
    }
    public PlantShop getPlantShopById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM PlantShop WHERE shopID = %d LIMIT 1", id);

        Cursor c = db.rawQuery(query, null);
        if(c.moveToNext()){
            PlantShop shop = new PlantShop();
            shop.setShopID(c.getInt(0));
            shop.setName(c.getString(1));
            shop.setImage(c.getString(2));
            shop.setLocation(c.getString(3));
            shop.setLatitude(c.getDouble(4));
            shop.setLongitude(c.getDouble(5));

            return shop;
        }
        return null;
    }

    /* ----------------------------------------------------- Favorite ------------------------------------------------------------- */

    public void insertFavorite(int shopID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("INSERT INTO Favorite VALUES (null, ?, ?)");
        statement.bindLong(1, Session.getInstance().getUser().getID());
        statement.bindLong(2, shopID);
        statement.executeInsert();
        db.close();
    }
    public boolean checkFavoriteState(int shopID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM Favorite WHERE shopID = %d AND userID = %d LIMIT 1", shopID, Session.getInstance().getUser().getID());
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() == 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }
    public ArrayList<Favorite> getAllFavoriteByUserID(){
        ArrayList<Favorite> favorites = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM Favorite WHERE userID = %d", Session.getInstance().getUser().getID());
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()){
            Favorite temp = new Favorite();
            temp.setFavoriteID(c.getInt(0));
            temp.setUserID(c.getInt(1));
            temp.setShopID(c.getInt(2));
            favorites.add(temp);
        }
        return favorites;
    }
    public ArrayList<PlantShop> getAllFavoriteShopsByUserID(){
        ArrayList<PlantShop> plantShops = new ArrayList<>();
        for(Favorite f : getAllFavoriteByUserID()){
            plantShops.add(getPlantShopById(f.getShopID()));
        };
        return plantShops;
    }
    public void removeFavorite(Integer shopID){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("DELETE FROM Favorite WHERE shopID = ? AND userID = ?");
        statement.bindLong(1, shopID);
        statement.bindLong(2, Session.getInstance().getUser().getID());
        statement.execute();
        db.close();
    }
}
