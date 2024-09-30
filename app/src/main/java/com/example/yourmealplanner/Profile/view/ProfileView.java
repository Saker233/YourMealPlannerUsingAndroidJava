package com.example.yourmealplanner.Profile.view;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface ProfileView {
    void showMealsForDate(List<Meal> meals);
    void showNoMealsMessage();
}
