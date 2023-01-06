package com.winston.plantin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.winston.plantin.DetailActivity;
import com.winston.plantin.R;
import com.winston.plantin.model.PlantShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.HView> {


        private Context context;
        private ArrayList<PlantShop> PlantShop;
        private int template;

        // View Holder class which
        // extends RecyclerView.ViewHolder
        public class HView extends RecyclerView.ViewHolder {

            // Text View
            TextView shopName, shopLocation;
            CardView plantShopCard;
            ImageView shopImage;

            // parameterised constructor for View Holder class
            // which takes the view as a parameter
            public HView(View view) {
                super(view);

                // initialise TextView with id
                plantShopCard = (CardView) view.findViewById(R.id.plant_shop_card);
                shopName = (TextView) view.findViewById(R.id.shop_name_txt);
                shopLocation = (TextView) view.findViewById(R.id.shop_loc_txt);
                shopImage = (ImageView) view.findViewById(R.id.shop_image);
            }
        }


        // Constructor for HomeAdapter class
        // which takes a PlantShop of String type
        public RecyclerAdapter(ArrayList<PlantShop> horizontalList, Context context, int template)
        {
            this.context = context;
            this.PlantShop = horizontalList;
            this.template = template;
        }

        // Override onCreateViewHolder which deals
        // with the inflation of the card layout
        // as an item for the RecyclerView.
        @Override
        public HView onCreateViewHolder(ViewGroup parent, int viewType)
        {

            // Inflate item.xml using LayoutInflator
            View itemView
                    = LayoutInflater
                    .from(parent.getContext())
                    .inflate(template,
                            parent,
                            false);

            // return itemView
            return new HView(itemView);
        }

    @Override
    public void onBindViewHolder(final HView holder, final int position)
    {
        Picasso.get().load(PlantShop.get(position).getImage()).into(holder.shopImage);
        holder.shopName.setText(PlantShop.get(position).getName());
        holder.shopLocation.setText(PlantShop.get(position).getLocation());
        int shopID = PlantShop.get(position).getShopID();
        holder.plantShopCard.setOnClickListener(v -> {
            Intent detail = new Intent(context, DetailActivity.class);
            detail.putExtra("shopID", shopID);
            context.startActivity(detail);
        });
    }


    @Override
    public int getItemCount()
    {
        return PlantShop.size();
    }
}
