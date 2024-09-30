package com.example.yourmealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.MealResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Meal> meal);

    @Query("SELECT * FROM meal")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * FROM meal WHERE idMeal = :id LIMIT 1")
    LiveData<Meal> getMealById(String id);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM Meal WHERE weekday = :weekday")
    LiveData<List<Meal>> getMealsByWeekday(String weekday);


    @Query("SELECT * FROM meal WHERE isFav = 1")
    LiveData<List<Meal>> getFavorites();

//    @Query("SELECT * FROM meal")
//    LiveData<List<Meal>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorite(Meal meal);


    @Query("SELECT * FROM Meal WHERE strArea = :countryName")
    List<Meal> getMealsByCountry(String countryName);

    @Query("SELECT * FROM Meal WHERE weekday IS NOT NULL")
    LiveData<List<Meal>> getAllMealsWithSpecificDays();

    @Query("SELECT * FROM Meal WHERE assignedDate = :selectedDate")
    LiveData<List<Meal>> getMealsByDate(String selectedDate);
}