package com.example.yourmealplanner.Home.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.view.MealView;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.MealResponse;
import com.example.yourmealplanner.Network.NetworkCallback;
//import com.example.yourmealplanner.Repo.RepoImp;
import com.example.yourmealplanner.database.MealsLocalDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealPresenterImp implements MealPresenter {

    private final MealsLocalDataSource local;
    private final MealRemoteDataSource remote;
    MealView mealView;
    private static final String TAG = "MealPresenterImp";
    private MealsLocalDataSource localDataSource;

    public MealPresenterImp(MealView mealView, MealsLocalDataSource local, MealRemoteDataSource remote) {
        this.mealView = mealView;
        this.local = local;
        this.remote = remote;
    }


    @Override
    public void addToFav(Meal meal) {
        local.changeFavoriteState(meal);
    }

    @Override
    public LiveData<List<Meal>> getFavorites() {
        return local.getFavorites();
    }

    @Override
    public void assignMealToWeekday(Meal meal, String weekday) {
        meal.setWeekday(weekday);
        local.insertMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getMealsForWeekday(String weekday) {
        return local.getMealsByWeekday(weekday);
    }

    @Override
    public void getMealDetails(String id) {
        Log.i(TAG, "getMealDetails: " + id);

        remote.getDetails(id, new NetworkCallback<Meal>() {
            @Override
            public void onSuccessResult_MEAL(Meal meal) {
                mealView.showDetails(meal);
                Log.d(TAG, "getMeal: Data received: " + meal);
            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {
                Log.e(TAG, "Error fetching meal details: " + errorMsg_meal);
                mealView.showErrorMsg("No meal details found");
            }

            @Override
            public void onSuccessResult_CAT(List<Category> categories) {

            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {

            }

            @Override
            public void onSuccessResult(Meal result) {

            }

            @Override
            public void onFailureResult(String errorMessage) {

            }


        });
    }
}