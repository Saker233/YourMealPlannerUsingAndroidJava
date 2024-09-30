package com.example.yourmealplanner.Profile.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yourmealplanner.Profile.view.OnLogOutClick;
import com.example.yourmealplanner.Profile.view.ProfileView;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProfilePresenter {
    private final ProfileView view;
    private final MealDao mealDao;
    private MealsLocalDataSource local;
    private final Context context;
    private final OnLogOutClick logOutClickListener;
    private final LifecycleOwner lifecycleOwner;

    public ProfilePresenter(ProfileView view, MealDao mealDao, MealsLocalDataSource local, Context context, OnLogOutClick logOutClickListener, LifecycleOwner lifecycleOwner) {
        this.view = view;
        this.mealDao = mealDao;
        this.local = local;
        this.context = context;
        this.logOutClickListener = logOutClickListener;
        this.lifecycleOwner = lifecycleOwner;
    }


    public void showShoppingList() {
        LiveData<List<Meal>> mealsLiveData = mealDao.getAllMeals();
        mealsLiveData.observe(lifecycleOwner, meals -> {
            if (meals != null && !meals.isEmpty()) {
                Map<String, Integer> aggregatedIngredients = aggregateIngredients(meals);
                view.displayAggregatedIngredients(aggregatedIngredients);
            } else {
                view.showNoMealsMessage();
            }
        });
    }


    public void getMealsForDate(String selectedDate) {
        Date date = parseDateString(selectedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(date);
        LiveData<List<Meal>> mealsLiveData = mealDao.getMealsByDate(formattedDate);
        Log.d("MealsDAO", "Fetching meals for date: " + formattedDate);
        mealsLiveData.observe(lifecycleOwner, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                Log.d("MealsObserver", "Meals fetched: " + (meals != null ? meals.size() : 0));

                if (meals != null && !meals.isEmpty()) {
                    view.showMealsForDate(meals);
                } else {
                    view.showNoMealsMessage();
                }
            }
        });
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            Log.e("ProfilePresenter", "Error parsing date: " + e.getMessage());
            return null;
        }
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

    private Map<String, Integer> aggregateIngredients(List<Meal> meals) {
        Map<String, Integer> ingredientMap = new HashMap<>();

        for (Meal meal : meals) {
            String ingredientsStr = meal.getStrIngredients();
            String[] ingredients = ingredientsStr.split("\n");

            for (String ingredient : ingredients) {
                if (!ingredient.trim().isEmpty()) {
                    ingredientMap.put(ingredient.trim(), ingredientMap.getOrDefault(ingredient.trim(), 0) + 1);
                }
            }
        }

        return ingredientMap;
    }
}
