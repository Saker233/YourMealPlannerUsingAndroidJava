package com.example.yourmealplanner.Profile.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yourmealplanner.Profile.view.OnLogOutClick;
import com.example.yourmealplanner.Profile.view.ProfileView;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.List;

public class ProfilePresenter {
    private final ProfileView view;
    private final MealDao mealDao;
    private MealsLocalDataSource local;
    private final Context context;
    private final OnLogOutClick logOutClickListener;
    private final LifecycleOwner lifecycleOwner;

    public ProfilePresenter(ProfileView view, MealDao mealDao, Context context, OnLogOutClick logOutClickListener, LifecycleOwner lifecycleOwner) {
        this.view = view; // Assign the view reference
        this.mealDao = mealDao;
        this.context = context;
        this.logOutClickListener = logOutClickListener;
        this.lifecycleOwner = lifecycleOwner;
    }





    public void getMealsForWeek() {
        mealDao.getAllMealsWithSpecificDays().observe((LifecycleOwner) view, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    view.showMealsForWeek(meals);
                } else {
                    view.showError("No meals assigned for the week.");
                }
            }
        });
    }

    public void removeMealFromWeek(Meal meal) {
        local.deleteMeal(meal);
    }

    public void logout() {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        logOutClickListener.onLogOut();
    }
}
