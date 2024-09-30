package com.example.yourmealplanner.Search.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("strIngredient")
    private String ingredientName;

    // Constructor
    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getImageUrl() {

        return "https://www.themealdb.com/images/ingredients/" + ingredientName + "-Small.png";
    }
}
