package com.winston.plantin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winston.plantin.R;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.fragment.DetailFragment;
import com.winston.plantin.fragment.FavoriteFragment;
import com.winston.plantin.model.PlantShop;
import com.squareup.picasso.Picasso;
import com.winston.plantin.utility.RecyclerListener;

import java.util.ArrayList;

public class FavoriteAdapter extends BaseAdapter {

    private Context ctx;
    private PlantinDB db;
    private ArrayList<PlantShop> favoriteShop;
    private RecyclerListener listener;

    public FavoriteAdapter(Context ctx, RecyclerListener listener){
        this.ctx = ctx;
        this.listener = listener;
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
            Bundle bundle = new Bundle();
            bundle.putInt("shopID", shopID);
            DetailFragment favFrag = new DetailFragment();
            favFrag.setArguments(bundle);
            listener.onItemClick(favFrag);
        });

        return convertView;
    }
}
