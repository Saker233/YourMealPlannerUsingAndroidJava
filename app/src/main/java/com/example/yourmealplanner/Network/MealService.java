package com.example.yourmealplanner.Network;

import com.example.yourmealplanner.Search.model.AreaResponse;

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



}
