package com.example.yourmealplanner.Home.presenter;

import com.example.yourmealplanner.Home.model.Meal;

public interface MealPresenter {

    void getMealDetails(String id);

    void addToFav(Meal meal);

}
