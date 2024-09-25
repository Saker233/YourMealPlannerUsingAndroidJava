package com.example.yourmealplanner.database;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface MealsLocalDataSource {




    /**
     * Inserts a single meal into the database.
     * @param meal The meal to be inserted.
     */
    void insertMeal(Meal meal);

    /**
     * Inserts multiple meals into the database.
     * @param meals The list of meals to be inserted.
     */
    void insertMeals(List<Meal> meals);

    /**
     * Changes the favorite state of a meal.
     * @param meal The meal whose favorite state is to be changed.
     */
    void changeFavoriteState(Meal meal);

    /**
     * Retrieves a LiveData list of favorite meals.
     * @return LiveData list of favorite meals.
     */
    LiveData<List<Meal>> getFavorites();

    /**
     * Retrieves a LiveData list of all meals.
     * @return LiveData list of all meals.
     */
    LiveData<List<Meal>> getAllMeals();

    /**
     * Retrieves a single meal based on its ID.
     * @param id The ID of the meal to retrieve.
     * @return LiveData of the single meal.
     */
    LiveData<Meal> getSingleMeal(String id);

    /**
     * Deletes a meal from the database.
     * @param meal The meal to be deleted.
     */
    void deleteMeal(Meal meal);

    /**
     * Updates an existing meal in the database.
     * @param meal The meal to be updated.
     */
    void updateMeal(Meal meal);


}
