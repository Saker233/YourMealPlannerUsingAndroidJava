package com.example.yourmealplanner.Home.model;

import com.example.yourmealplanner.Network.MealCallback;
import com.example.yourmealplanner.Network.MealResponse;
import com.example.yourmealplanner.Network.MealService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient {
    private static MealClient instance;
    private MealService mealService;

    private MealClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealClient getInstance() {
        if (instance == null) {
            instance = new MealClient();
        }
        return instance;
    }

    public void getMealsByCategory(String categoryName, MealCallback<List<Meal>> callback) {
        Call<MealResponse> call = mealService.getMealsByCategory(categoryName);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, retrofit2.Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals(); // Assuming MealResponse has a getMeals() method
                    if (meals != null && !meals.isEmpty()) {
                        callback.onSuccess(meals);
                    } else {
                        callback.onError(new Throwable("No meals found"));
                    }
                } else {
                    callback.onError(new Throwable("Failed to fetch meals"));
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
