package com.example.yourmealplanner.Home.presenter;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface MealPresenter {

    void getMealDetails(String id);

    void addToFav(Meal meal);

    LiveData<List<Meal>> getFavorites();

}
