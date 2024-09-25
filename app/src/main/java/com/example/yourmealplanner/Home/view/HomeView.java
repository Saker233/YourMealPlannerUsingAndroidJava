package com.example.yourmealplanner.Home.view;

import com.example.yourmealplanner.Home.model.Meal;

import com.example.yourmealplanner.Home.model.Category;

import java.util.List;

public interface HomeView {

    void displayRandomMeal(Meal meal);
    void showError(String errorMessage);
    void displayCategories(List<Category> categories);
}
