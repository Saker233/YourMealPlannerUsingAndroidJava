package com.example.yourmealplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.yourmealplanner.Notification.DailyMealReceiver;
import com.example.yourmealplanner.Notification.MyNotificationHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
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

            // Check for navigation extra
            String navigateTo = getIntent().getStringExtra("navigate_to");
            if (navigateTo != null) {
                if (navigateTo.equals("profile")) {
                    navController.navigate(R.id.nav_profile); // Navigate to profile fragment
                }
            } else if (isUserLoggedIn()) {
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

        MyNotificationHelper.createNotificationChannel(this);
        scheduleDailyMealNotification();

    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isGuest = preferences.getBoolean("isGuest", false);
        return !isGuest;
    }

    public void scheduleDailyMealNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DailyMealReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 9);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);

        long triggerTime = System.currentTimeMillis() + 30 * 1000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);




//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("MainActivity", "Daily meal notification scheduled for 9:00 AM.");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (intent != null && intent.hasExtra("navigate_to")) {
            String destination = intent.getStringExtra("navigate_to");
            if ("profile".equals(destination)) {
                navController.navigate(R.id.nav_profile);
            }
        }
    }
}




