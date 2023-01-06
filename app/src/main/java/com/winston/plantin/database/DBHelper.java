package com.winston.plantin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {
    private final String CREATE_USER = "CREATE TABLE User (" +
            "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "email TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "phoneNumber TEXT NOT NULL )";

    private final String CREATE_PLANTSHOP = "CREATE TABLE PlantShop (" +
            "shopID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "image TEXT NOT NULL," +
            "location TEXT NOT NULL," +
            "latitude REAL NOT NULL," +
            "longitude REAL NOT NULL)";


    private final String CREATE_FAVORITE = "CREATE TABLE Favorite (" +
            "favoriteID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "userID INTEGER NOT NULL," +
            "shopID INTEGER NOT NULL," +
            "FOREIGN KEY(userID) REFERENCES User(userID)," +
            "FOREIGN KEY(shopID) REFERENCES PlantShop(shopID))";

    public DBHelper(@Nullable Context context) {
        super(context, "Plantin", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PLANTSHOP);
        db.execSQL(CREATE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
