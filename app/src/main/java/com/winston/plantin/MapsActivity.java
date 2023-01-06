package com.winston.plantin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.winston.plantin.databinding.ActivityMapsBinding;
import com.winston.plantin.utility.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent map = getIntent();
        LatLng currShop = new LatLng(map.getDoubleExtra("latitude", 0), map.getDoubleExtra("longitude", 0));
        mMap.addMarker(new MarkerOptions().position(currShop).title(map.getStringExtra("name")));
        CameraPosition cam = new CameraPosition.Builder().target(currShop).zoom(17).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cam));
    }
}