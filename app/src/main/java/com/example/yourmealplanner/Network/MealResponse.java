package com.example.yourmealplanner.Network;


import com.example.yourmealplanner.Home.model.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    public List<Meal> meal;


    public List<Meal> getMeals() {
        return meal;
    }

    public void setMeals(List<Meal> meals) {
        this.meal = meals;
    }
}



