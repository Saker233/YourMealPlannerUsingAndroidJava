package com.example.yourmealplanner.Repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.Search.model.Ingredient;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.List;

public class RepositoryImpl implements Repository {

    private final MealsLocalDataSourceImp localDataSource;
    private final MealRemoteDataSource remoteDataSource;

    private static RepositoryImpl instance;

    private RepositoryImpl(Context context) {
        this.localDataSource = MealsLocalDataSourceImp.getInstance(context);
        this.remoteDataSource = MealRemoteDataSource.getInstance(context);
    }

    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return localDataSource.getAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getFavoriteMealsForUser(String userId) {
        return localDataSource.getFavoriteMealsForUser(userId);
    }

    @Override
    public LiveData<List<Meal>> getMealsForUser(String userId) {
        return localDataSource.getMealsForUser(userId);
    }

    @Override
    public void addToFavorite(Meal meal) {
        localDataSource.addToFavorite(meal);
    }

    @Override
    public void removeFromFavorite(Meal meal) {
        localDataSource.removeFromFavorite(meal);
    }

    @Override
    public void clearAssignedDate(Meal meal) {
        localDataSource.clearAssignedDate(meal);
    }

    @Override
    public void addMealToDay(Meal meal) {
        localDataSource.addMealToDay(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        localDataSource.deleteMeal(meal);
    }

    @Override
    public void getRandom(NetworkCallback networkCallback) {
        remoteDataSource.getRandom(networkCallback);
    }

    @Override
    public void getMealDetails(String id, NetworkCallback networkCallback) {
        remoteDataSource.getDetails(id, networkCallback);
    }

    @Override
    public void getMealsByCategory(String categoryName, NetworkCallback<List<Meal>> callback) {
        remoteDataSource.getMealsByCategory(categoryName, callback);
    }

    @Override
    public void fetchMealsByCountry(String countryName, NetworkCallback<List<Meal>> networkCallback) {
        remoteDataSource.fetchMeals(countryName, networkCallback);
    }

    @Override
    public void getAreas(NetworkCallback networkCallback) {
        remoteDataSource.getAreas(networkCallback);
    }

    @Override
    public void getIngredients(NetworkCallback<List<Ingredient>> callback) {
        remoteDataSource.getIngredients(callback);
    }



    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallback<List<Meal>> callback) {
        remoteDataSource.getMealsByIngredient(ingredient, callback);
    }
}