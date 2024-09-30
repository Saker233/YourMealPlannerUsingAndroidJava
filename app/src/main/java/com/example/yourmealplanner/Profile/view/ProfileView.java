package com.example.yourmealplanner.Profile.view;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;
import java.util.Map;

public interface ProfileView {
    void showMealsForDate(List<Meal> meals);
    void showNoMealsMessage();
    void displayAggregatedIngredients(Map<String, Integer> ingredientsMap);
}
