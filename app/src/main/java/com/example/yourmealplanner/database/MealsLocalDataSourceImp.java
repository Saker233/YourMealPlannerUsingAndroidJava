package com.example.yourmealplanner.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public class MealsLocalDataSourceImp implements MealsLocalDataSource {

    private MealDao mealDao;

    private static final String TAG = "MealsLocalDataSourceImpl";

    private static MealsLocalDataSourceImp localSource = null;

    private LiveData<List<Meal>> meals;

    private String currentUserId;


    private MealsLocalDataSourceImp(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
        meals = mealDao.getAllMeals();
        currentUserId = getCurrentUserId(context);
    }

    private String getCurrentUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getString("current_user_id", null);
    }

    public static MealsLocalDataSourceImp getInstance(Context context) {
        Log.d("LOCALDataSource", "getInstance");
        if (localSource == null) {
            localSource = new MealsLocalDataSourceImp(context);
        }
        return localSource;
    }


    @Override
    public void addToFavorite(Meal meal) {
        new Thread(() -> {
            meal.setFav(true);
            mealDao.insertMeal(meal);
        }).start();
    }

    @Override
    public void removeFromFavorite(Meal meal) {
        new Thread(() -> {
            meal.setFav(false);
            if (meal.hasAssignedDate()) {
                mealDao.insertMeal(meal);
            } else {
                mealDao.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public void clearAssignedDate(Meal meal) {
        new Thread(() -> {
            meal.setAssignedDate(null);
            mealDao.deleteMeal(meal);
        }).start();


    }

    @Override
    public LiveData<List<Meal>> getFavoriteMealsForUser(String userId) {
        return mealDao.getFavoriteMealsForUser(userId);
    }

    @Override
    public LiveData<List<Meal>> getMealsForUser(String userId) {
        return mealDao.getMealsForUser(userId);
    }

    @Override
    public void changeFavoriteState(Meal meal) {
        new Thread(() -> {
            Meal currentMeal = mealDao.getMealById(meal.getIdMeal()).getValue();
            if (currentMeal != null) {
                currentMeal.setFav(!currentMeal.isFav());
                mealDao.insertMeal(currentMeal);
                Log.d(TAG, "Favorite state changed for meal: " + meal.getIdMeal());
            } else {
                Log.e(TAG, "Meal not found in database for ID: " + meal.getIdMeal());
            }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDao.deleteMeal(meal);
            }
        }).start();
    }
    @Override
    public void addMealToDay(Meal meal) {
        new Thread(() -> {
            if (meal.isFav()) {
                meal.setAssignedDate(meal.getAssignedDate());
            } else {
                meal.setAssignedDate(meal.getAssignedDate());
            }
            mealDao.insertMeal(meal);
        }).start();

    }



    @Override
    public LiveData<List<Meal>> getFavorites() {
        return mealDao.getFavorites();
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return mealDao.getAllMeals();
    }

    @Override
    public LiveData<Meal> getSingleMeal(String id) {
        return mealDao.getMealById(id);
    }


    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                meal.setUserId(currentUserId);
                mealDao.insertMeal(meal);


            }
        }).start();
    }

    @Override
    public void insertMeals(List<Meal> meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                mealDao.insertMeals(meal);
            }
        }).start();
    }

    public boolean isFavorite(String mealId) {
        LiveData<List<Meal>> favoritesLiveData = mealDao.getFavorites();
        return false;
    }
    public LiveData<List<Meal>> getMealsByWeekday(String weekday) {
        return mealDao.getMealsByWeekday(weekday);
    }

    @Override
    public MealDao getMealDao() {
        return mealDao;
    }


}
