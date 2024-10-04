package com.example.yourmealplanner.Home.presenter;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.Date;
import java.util.List;

public interface MealPresenter {

    void getMealDetails(String id);

    void addToFav(Meal meal);

    LiveData<List<Meal>> getFavorites();

    void assignMealToWeekday(Meal meal, String weekday);


    LiveData<List<Meal>> getMealsForWeekday(String weekday);

    void assignMealToDate(Meal meal, Date date, Meal.MealType mealType);

}
