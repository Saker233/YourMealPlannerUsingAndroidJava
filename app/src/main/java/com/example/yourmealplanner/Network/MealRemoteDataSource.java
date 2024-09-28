package com.example.yourmealplanner.Network;

import android.content.Context;
import android.util.Log;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.model.MealClient;
import com.example.yourmealplanner.Home.view.HomeView;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.model.AreaResponse;
import com.example.yourmealplanner.database.AppDataBase;
import com.example.yourmealplanner.database.MealDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealRemoteDataSource";
    MealService mealService;
    CategoryService categoryService;
    MealDao mealDao;
    AppDataBase db;
    private Context context;
    private static MealRemoteDataSource instance;
    private HomeView view;


    private MealRemoteDataSource(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mealService = retrofit.create(MealService.class);
        categoryService = retrofit.create(CategoryService.class);
    }


    public static synchronized MealRemoteDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MealRemoteDataSource(context);
        }
        return instance;
    }

    public void getRandom(NetworkCallback networkCallback) {
        mealService.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "MealResponse: " + response.body());
//                    if (!response.body().getMeals().isEmpty()) {
                        Meal randomMeal = response.body().getMeals().get(0);
                        networkCallback.onSuccessResult_MEAL(randomMeal);
//                    } else {
//                        Log.e(TAG, "No meals found in response");
//                        networkCallback.onFailureResult_MEAL("No meal found in response");
//                    }
                } else {
                    Log.e(TAG, "API call unsuccessful or response body is null: " + response.message());
                    networkCallback.onFailureResult_MEAL("Failed to fetch meal details");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.e(TAG, "Error fetching meal: " + throwable.getMessage());
                networkCallback.onFailureResult_MEAL(throwable.getMessage());
            }
        });
    }

    public void getDetails(String id, NetworkCallback networkCallback) {
        mealService.getMealDetails(id).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getMeals().isEmpty()) {

                    Meal mealDetail = response.body().getMeals().get(0);
                    networkCallback.onSuccessResult_MEAL(mealDetail);
                } else {
                    networkCallback.onFailureResult_MEAL("No meal found in response");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                networkCallback.onFailureResult_MEAL(throwable.getMessage());
            }
        });
    }

    public void getCategories(NetworkCallback networkCallback) {
        categoryService.getCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse != null && categoryResponse.categories != null) {
                        networkCallback.onSuccessResult_CAT(categoryResponse.categories);
                    } else {
                        Log.e(TAG, "Categories list is null");
                        networkCallback.onFailureResult_CAT("No categories found");
                    }
                } else {
                    Log.e(TAG, "API call unsuccessful or response body is null");
                    networkCallback.onFailureResult_CAT("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                networkCallback.onFailureResult_CAT(throwable.getMessage());
            }
        });
    }

    public void getMealsByCategory(String categoryName, NetworkCallback<List<Meal>> callback) {
        MealClient.getInstance().getMealsByCategory(categoryName, new MealCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                callback.onSuccessResult(meals);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailureResult_CAT(t.getMessage());
            }
        });
    }

    public void fetchMeals(String countryName, NetworkCallback<List<Meal>> networkCallback) {
        String trimmedCountryName = countryName.trim();

        Log.d(TAG, "Fetching meals for: " + trimmedCountryName);
        mealService.getMealsByArea(countryName).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response body: " + response.body());
                    List<Meal> meals = response.body().getMeals();
                    networkCallback.onSuccessResult(meals);
                } else {
                    Log.e(TAG, "No meals found or response body is null.");
                    networkCallback.onFailureResult("No meals found for the selected country.");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching meals: " + t.getMessage());
                networkCallback.onFailureResult(t.getMessage());
            }
        });
    }


    public void getAreas(NetworkCallback<List<Area>> callback) {
        mealService.getAreas().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Area> areas = response.body().getAreas();
                    callback.onSuccessResult(areas);
                } else {
                    callback.onFailureResult("Failed to fetch areas");
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                callback.onFailureResult(t.getMessage());
            }
        });
    }


}





