package com.winston.plantin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.winston.plantin.databinding.ActivityMainBinding;
import com.winston.plantin.fragment.FavoriteFragment;
import com.winston.plantin.fragment.HomeFragment;
import com.winston.plantin.fragment.SettingFragment;
import com.winston.plantin.utility.Session;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static Fragment homeFragment = new HomeFragment();
    private static Fragment favoriteFragment = new FavoriteFragment();
    private static Fragment settingsFragment = new SettingFragment();
    private static Fragment lastFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(lastFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    lastFragment = homeFragment;
                    break;
                case R.id.favorite:
                    lastFragment = favoriteFragment;
                    break;
                case R.id.settings:
                    lastFragment  = settingsFragment;
                    break;
            }
            replaceFragment(lastFragment);
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
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
            case R.id.setting_btn:
                setting();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setting() {
        replaceFragment(new SettingFragment());
    }

    private void logout(){
        Session.getInstance().logout();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

}