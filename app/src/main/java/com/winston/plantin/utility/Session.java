package com.winston.plantin.utility;

import android.content.Context;

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

    public void loadTheme(Context ctx) {
        boolean isNight = PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.key_theme), false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public boolean isEnableNotification(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(ctx.getString(R.string.key_notifications), false);
    }
}
