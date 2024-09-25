package com.example.yourmealplanner.Home.view;

import com.example.yourmealplanner.Home.model.Meal;

public interface MealView {
    void showDetails(Meal meal);

    void showErrorMsg(String error);
}
