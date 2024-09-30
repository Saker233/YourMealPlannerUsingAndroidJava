package com.example.yourmealplanner.Network;

import com.example.yourmealplanner.Search.model.AreaResponse;
import com.example.yourmealplanner.Search.model.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("lookup.php?i=")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);

    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    @GET("list.php?a=list")
    Call<AreaResponse> getAreas();

    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();


    @GET("filter.php")
    Call<MealResponse> getMealsByIngredient(@Query("i") String ingredient);




}
