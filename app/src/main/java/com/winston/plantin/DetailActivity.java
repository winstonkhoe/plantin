package com.winston.plantin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.PlantShop;
import com.winston.plantin.utility.Session;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private PlantinDB db;

    private ImageView shopImage;
    private TextView shopName, shopLocation;
    private Button mapBtn;
    private ImageButton favBtn;

    private int currShopID;
    private PlantShop currPlantShop;
    private boolean initialFavState;

    private void initComponents(){
        db = new PlantinDB(this);
        shopName = findViewById(R.id.shop_name_txt);
        shopLocation = findViewById(R.id.shop_loc_txt);
        shopImage = findViewById(R.id.shop_image);
        mapBtn = findViewById(R.id.map_btn);
        favBtn = findViewById(R.id.fav_btn);
    }

    private void loadData(){
        Picasso.get().load(currPlantShop.getImage()).into(shopImage);
        shopName.setText(currPlantShop.getName());
        shopLocation.setText(currPlantShop.getLocation());
        initialFavState = db.checkFavoriteState(currShopID);
        checkFavState();
        mapBtn.setOnClickListener(v->{
            Intent map = new Intent(this, MapsActivity.class);
            map.putExtra("name", currPlantShop.getName());
            map.putExtra("latitude", currPlantShop.getLatitude());
            map.putExtra("longitude", currPlantShop.getLongitude());
            startActivity(map);
        });

        favBtn.setOnClickListener(v->{
            initialFavState = !initialFavState;
            updateFav(initialFavState);
            checkFavState();
        });
    }

    private void checkFavState()
    {
        if(db.checkFavoriteState(currShopID) == true)
        {
            favBtn.setImageResource(R.drawable.ic_heart_filled);
        }
        else
        {
            favBtn.setImageResource(R.drawable.ic_heart_stroked);
        };
    }

    private void updateFav(boolean favorite)
    {
        if(favorite == true)
        {
            db.insertFavorite(currShopID);
        }
        else
        {
            db.removeFavorite(currShopID);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new PlantinDB(this);
        initComponents();

        Intent detail = getIntent();
        currShopID = detail.getIntExtra("shopID", 1);
        currPlantShop = db.getPlantShopById(currShopID);

        loadData();

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


    private void redirect(){
        Intent fav = new Intent(this, FavoriteActivity.class);
        startActivity(fav);
    }

    private void logout(){
        Session.getInstance().logout();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}