package com.winston.plantin.utility;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.winston.plantin.R;
import com.winston.plantin.model.User;

public class Session {
    private User user;
    private static Session instance;

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void logout(){
        user = null;
    }

    public boolean isNight(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.key_theme), false);
    }

    public void loadTheme(Context ctx) {
        boolean isNight = isNight(ctx);
//        Log.d("PREFERENCE", "loadTheme: " + isNight);
        changeTheme(isNight);
    }

    public void changeTheme(Boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public boolean isEnableNotification(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.key_notifications), false);
    }
}
