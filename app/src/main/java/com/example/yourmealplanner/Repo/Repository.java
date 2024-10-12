package com.example.yourmealplanner.Repo;


import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.Search.model.Ingredient;

import java.util.List;


public interface Repository {

    LiveData<List<Meal>> getAllMeals();
    LiveData<List<Meal>> getFavoriteMealsForUser(String userId);
    LiveData<List<Meal>> getMealsForUser(String userId);
    void addToFavorite(Meal meal);
    void removeFromFavorite(Meal meal);
    void clearAssignedDate(Meal meal);
    void addMealToDay(Meal meal);
    void deleteMeal(Meal meal);

    void getRandom(NetworkCallback networkCallback);
    void getMealDetails(String id, NetworkCallback networkCallback);
    void getMealsByCategory(String categoryName, NetworkCallback<List<Meal>> callback);
    void fetchMealsByCountry(String countryName, NetworkCallback<List<Meal>> networkCallback);
    void getAreas(NetworkCallback networkCallback);
    void getIngredients(NetworkCallback<List<Ingredient>> callback);
    void getMealsByIngredient(String ingredient, NetworkCallback<List<Meal>> callback);
}
