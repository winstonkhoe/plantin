package com.winston.plantin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.winston.plantin.utility.Session;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session.getInstance().loadTheme(this);
        setContentView(R.layout.activity_login);
    }
}