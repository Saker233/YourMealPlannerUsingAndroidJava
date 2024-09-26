package com.example.yourmealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;




@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeals(List<Meal> meal);

    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * FROM meal WHERE idMeal = :id LIMIT 1")
    LiveData<Meal> getMealById(String id);

    @Delete
    void deleteMeal(Meal meal);

//    @Query("SELECT * FROM meal WHERE isFav = true")
//    LiveData<List<Meal>> getFavorites();

    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorite(Meal meal);



}