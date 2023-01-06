package com.winston.plantin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winston.plantin.adapter.FavoriteAdapter;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.utility.Session;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView favoriteRecyclerView;
    private PlantinDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        db = new PlantinDB(this);

        FavoriteAdapter favAdapter = new FavoriteAdapter(this);
        ListView favList = findViewById(R.id.favorite_list_view);
        favList.setAdapter(favAdapter);
//        favoriteRecyclerView = (RecyclerView)findViewById(R.id.favorite_recyclerview);
//        setRecyclerView(db.getAllFavoriteShopsByUserID(), favoriteRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_btn:
                logout();
                break;
            case R.id.favorite_page_btn:
                redirect();
                break;
        }
        return super.onOptionsItemSelected(item);
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


//    private void setRecyclerView(ArrayList<PlantShop> data, RecyclerView recyclerView)
//    {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(new RecyclerAdapter(data, this, R.layout.item_full_size));
//    }
}