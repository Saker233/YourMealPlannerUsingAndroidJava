package com.example.yourmealplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public class MealsLocalDataSourceImp implements MealsLocalDataSource {

    private MealDao mealDao;

    private static final String TAG = "MealsLocalDataSourceImpl";

    private static MealsLocalDataSourceImp localSource = null;

    private LiveData<List<Meal>> meals;


    public MealsLocalDataSourceImp(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
        meals = mealDao.getAllMeals();
    }

    public static MealsLocalDataSourceImp getInstance(Context context) {
        Log.d("LOCALDataSource", "getInstance");
        if (localSource == null) {
            localSource = new MealsLocalDataSourceImp(context);
        }
        return localSource;
    }


    @Override
    public void changeFavoriteState(Meal meal) {
//        meal.setFav(!meal.getFav());
        new Thread(new Runnable() {
            @Override
            public void run() {
                meal.setFav(true);
                mealDao.addToFavorite(meal);

                Log.d("MealRepository", "Favorite state changed for meal: " + meal.getIdMeal());
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
    public void updateMeal(Meal meal) {

    }


    @Override
    public LiveData<List<Meal>> getFavorites() {
        return mealDao.getFavorites();
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return null;
    }

    @Override
    public LiveData<Meal> getSingleMeal(String id) {
        return null;
    }


    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                meal.setFav(false);
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

        List<Meal> favorites = favoritesLiveData.getValue();

        if (favorites != null) {
            for (Meal meal : favorites) {
                if (meal.getIdMeal().equals(mealId)) {
                    return true;
                }
            }
        }
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
