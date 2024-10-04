package com.example.yourmealplanner.Home.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.yourmealplanner.Home.view.HomeView;
import com.example.yourmealplanner.Network.CategoryResponse;
import com.example.yourmealplanner.Network.CategoryService;
import com.example.yourmealplanner.Network.NetworkCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryClient {
    private static CategoryClient instance;
    private CategoryService categoryService;
    private HomeView view;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private CategoryClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        categoryService = retrofit.create(CategoryService.class);
        Log.d("CategoryClient", "CategoryService initialized: " + (categoryService != null));
    }

    public static CategoryClient getInstance() {
        if (instance == null) {
            instance = new CategoryClient();
        }
        return instance;
    }

    public void getCategories(NetworkCallback<List<Category>> callback) {
        if (categoryService == null) {
            callback.onFailureResult_CAT("CategoryService is null");
            return;
        }


        Call<CategoryResponse> call = categoryService.getCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse != null && categoryResponse.categories != null) {
                    } else {
                        Log.e("MealRemoteDataSource", "Categories list is null or CategoryResponse is null");
                    }
                } else {
                    Log.e("MealRemoteDataSource", "Response unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("MealRemoteDataSource", "API call failed: " + t.getMessage());
            }
        });
    }
}
