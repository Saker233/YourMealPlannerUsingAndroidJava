package com.example.yourmealplanner.Profile.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.yourmealplanner.Profile.view.ProfileView;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.List;

public class ProfilePresenter {
    private ProfileView view;
    private MealDao mealDao;
    private MealsLocalDataSource local;

    public ProfilePresenter(ProfileView view, MealDao mealDao, Context context) {
        this.view = view;
        this.mealDao = mealDao;
        this.local = MealsLocalDataSourceImp.getInstance(context);
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
}
