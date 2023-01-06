package com.winston.plantin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winston.plantin.DetailActivity;
import com.winston.plantin.R;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.PlantShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends BaseAdapter {

    private Context ctx;
    private PlantinDB db;
    private ArrayList<PlantShop> favoriteShop;

    public FavoriteAdapter(Context ctx){
        this.ctx = ctx;
        db = new PlantinDB(ctx);
        favoriteShop = db.getAllFavoriteShopsByUserID();
    }

    @Override
    public int getCount() {
        return favoriteShop.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        convertView = inflater.inflate(R.layout.item_full_size, null);

        ImageView shopImage = convertView.findViewById(R.id.shop_image);
        TextView shopName = convertView.findViewById(R.id.shop_name_txt);
        TextView shopLocation = convertView.findViewById(R.id.shop_loc_txt);

        PlantShop shop = db.getPlantShopById(favoriteShop.get(position).getShopID());
        Picasso.get().load(shop.getImage()).into(shopImage);
        shopLocation.setText(shop.getLocation());
        shopName.setText(shop.getName());
        int shopID = shop.getShopID();
        convertView.setOnClickListener(v->{
            Intent detail = new Intent(ctx, DetailActivity.class);
            detail.putExtra("shopID", shopID);
            ctx.startActivity(detail);
        });

        return convertView;
    }
}
