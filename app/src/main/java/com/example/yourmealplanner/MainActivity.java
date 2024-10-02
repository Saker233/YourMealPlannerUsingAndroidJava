package com.example.yourmealplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.CategoryClient;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate: Setting content view");
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Log.d("MainActivity", "BottomNavigationView initialized");

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            Log.d("MainActivity", "NavHostFragment found, setting up NavController");
            navController = navHostFragment.getNavController();


            if (isUserLoggedIn()) {
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
                Log.d("MainActivity", "NavController set up with BottomNavigationView for logged-in user");
            } else {
                bottomNavigationView.getMenu().clear();
                bottomNavigationView.inflateMenu(R.menu.guest_menu);
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
                navController.navigate(R.id.nav_profile);
                Log.d("MainActivity", "NavController set up with BottomNavigationView for guest user");
            }
        } else {
            Log.e("MainActivity", "NavHostFragment is null. Cannot set up NavController.");
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isGuest = preferences.getBoolean("isGuest", false);
        return !isGuest;
    }
}




