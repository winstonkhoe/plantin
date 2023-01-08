package com.winston.plantin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.winston.plantin.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_setting, new SettingFragment())
                .commit();
    }
}