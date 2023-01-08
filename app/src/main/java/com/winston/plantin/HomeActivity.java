package com.winston.plantin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winston.plantin.adapter.RecyclerAdapter;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.PlantShop;
import com.winston.plantin.utility.APIManager;
import com.winston.plantin.utility.Session;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView allShopRecyclerView;
    private PlantinDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new PlantinDB(this);
        allShopRecyclerView = (RecyclerView) findViewById(R.id.all_shop_recyclerview);
        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_btn:
                logout();
                break;
            case R.id.favorite_page_btn:
                redirect();
                break;
            case R.id.theme_btn:
                toggleTheme();
                break;
            case R.id.setting_btn:
                setting();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem themeBtn = menu.findItem(R.id.theme_btn);
        if (Session.getInstance().isNight()) {
            themeBtn.setIcon(R.drawable.ic_baseline_mode_night_24);
        } else {
            themeBtn.setIcon(R.drawable.ic_baseline_light_mode_24);
        }
        return true;
    }

    private void toggleTheme() {
        if (Session.getInstance().isNight()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void redirect() {
        Intent fav = new Intent(this, FavoriteActivity.class);
        startActivity(fav);
    }

    private void logout() {
        Session.getInstance().logout();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    private void fetchData() {
        if (!db.checkPlantShopData()) {
            APIManager manager = new APIManager(this);
            if (manager.fetchData()) {
                setRecyclerView(db.getAllPlantShop(), allShopRecyclerView);
            }
        } else {
            setRecyclerView(db.getAllPlantShop(), allShopRecyclerView);
        }
    }

    private void setRecyclerView(ArrayList<PlantShop> data, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(data, this, R.layout.item_full_size));
    }
}