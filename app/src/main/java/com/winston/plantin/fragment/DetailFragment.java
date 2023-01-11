package com.winston.plantin.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.winston.plantin.R;
import com.winston.plantin.database.PlantinDB;
import com.winston.plantin.model.PlantShop;
import com.winston.plantin.utility.Session;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PlantinDB db;

    private ImageView shopImage;
    private TextView shopName, shopLocation;
    private Button mapBtn;
    private ImageButton favBtn;

    private int currShopID;
    private PlantShop currPlantShop;
    private boolean initialFavState;


    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;


    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            if (Session.getInstance().isEnableNotification(this.getContext())) {
                addfavNotification();
            }
        }
        else
        {
            db.removeFavorite(currShopID);
            if (Session.getInstance().isEnableNotification(this.getContext())) {
                deletefavNotification();
            }
        }
    }

    private void loadData(){
        Picasso.get().load(currPlantShop.getImage()).into(shopImage);
        shopName.setText(currPlantShop.getName());
        shopLocation.setText(currPlantShop.getLocation());
        initialFavState = db.checkFavoriteState(currShopID);
        checkFavState();
        mapBtn.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("name", currPlantShop.getName());
            bundle.putDouble("latitude", currPlantShop.getLatitude());
            bundle.putDouble("longitude", currPlantShop.getLongitude());
            MapsFragment mapFrag = new MapsFragment();
            mapFrag.setArguments(bundle);
            replaceFragment(mapFrag);
        });

        favBtn.setOnClickListener(v->{
            initialFavState = !initialFavState;
            updateFav(initialFavState);
            checkFavState();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        db = new PlantinDB(this.getContext());
        shopName = view.findViewById(R.id.shop_name_txt);
        shopLocation = view.findViewById(R.id.shop_loc_txt);
        shopImage = view.findViewById(R.id.shop_image);
        mapBtn = view.findViewById(R.id.map_btn);
        favBtn = view.findViewById(R.id.fav_btn);
        currShopID = getArguments().getInt("shopID", 1);
        currPlantShop = db.getPlantShopById(currShopID);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("FAVOURITE_NOTIFICATION", "FAVOURITE", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        loadData();
        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
        );
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void addfavNotification() {
        notificationManager = NotificationManagerCompat.from(this.getContext());
        builder = new NotificationCompat.Builder(this.getContext(), "FAVOURITE_NOTIFICATION")
                .setSmallIcon(R.drawable.logo_plantin)
                .setContentTitle("FAVOURITE")
                .setContentText("You have add " + shopName.getText() + " as one of your favourite shop")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }

    private void deletefavNotification() {
        notificationManager = NotificationManagerCompat.from(this.getContext());
        builder = new NotificationCompat.Builder(this.getContext(), "FAVOURITE_NOTIFICATION")
                .setSmallIcon(R.drawable.logo_plantin)
                .setContentTitle("FAVOURITE")
                .setContentText("You have removed " + shopName.getText() + " from your favourite shop")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }
}