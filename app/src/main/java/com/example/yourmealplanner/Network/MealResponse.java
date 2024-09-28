package com.example.yourmealplanner.Network;


import com.example.yourmealplanner.Home.model.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    public List<Meal> meals;


    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}



