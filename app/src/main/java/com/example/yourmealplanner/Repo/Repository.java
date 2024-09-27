package com.example.yourmealplanner.Repo;


import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.NetworkCallback;

import java.util.List;

public interface Repository {

    /**
     * Fetches a random meal from the remote data source.
     */
    void getRandomMeal(NetworkCallback<Meal> callback);

    /**
     * Fetches meal details by meal ID from the remote data source.
     * @param mealId The ID of the meal to fetch.
     * @param callback The callback to handle success or failure.
     */
    void getMealDetails(String mealId, NetworkCallback<Meal> callback);

    /**
     * Fetches a list of meals from the remote data source based on category.
     * @param categoryName The name of the category.
     * @param callback The callback to handle success or failure.
     */
    void getMealsByCategory(String categoryName, NetworkCallback<List<Meal>> callback);

    /**
     * Inserts a single meal into the local data source.
     * @param meal The meal to be inserted.
     */
    void insertMeal(Meal meal);

    /**
     * Retrieves a LiveData list of favorite meals from the local data source.
     * @return LiveData list of favorite meals.
     */
    LiveData<List<Meal>> getFavorites();

    /**
     * Changes the favorite state of a meal in the local data source.
     * @param meal The meal whose favorite state is to be changed.
     */
    void changeFavoriteState(Meal meal);
}

