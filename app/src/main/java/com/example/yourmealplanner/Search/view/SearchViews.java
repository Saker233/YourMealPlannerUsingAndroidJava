package com.example.yourmealplanner.Search.view;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.model.Ingredient;

import java.util.List;

public interface SearchViews {
    void displayCountries(List<Area> areas);
    void displayMeals(List<Meal> meals);
    void displayError(String message);
    void showIngredients(List<Ingredient> ingredients);
}
