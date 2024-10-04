package com.example.yourmealplanner.Home.presenter;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.view.HomeView;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.database.MealsLocalDataSource;

import java.util.List;
import java.util.Random;

public class HomePresenter implements NetworkCallback<Meal> {

    private HomeView view;
    private MealsLocalDataSource local;
    private MealRemoteDataSource remote;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    private boolean isMealFetching = false;

    public HomePresenter(Context context, HomeView view, MealsLocalDataSource local, MealRemoteDataSource remote) {
        this.view = view;
        this.context = context;
        this.local = local;
        this.remote = remote;

    }

    public void getRandomMeal() {
        Log.d("TAG", "getRandomMeal called");
        if (!isMealFetching) {
            remote.getRandom(this);
            isMealFetching = true;
        } else {
            Log.d("TAG", "Fetching already in progress");
        }
    }

    public void getCategories() {
        remote.getCategories(this);
    }






    @Override
    public void onSuccessResult_MEAL(Meal meal) {
        if (meal == null || meal.getIdMeal() == null) {
            view.showError("Meal data is not available");
            isMealFetching = false;
            return;
        }

        view.displayRandomMeal(meal);
        Log.d("TAG", "Meal Displayed - ID: " + meal.getIdMeal());
        isMealFetching = false;
    }

    @Override
    public void onFailureResult_MEAL(String errorMsg_meal) {
        view.showError(errorMsg_meal);
        isMealFetching = false;
    }



    @Override
    public void onSuccessResult_CAT(List<Category> categories) {
        view.displayCategories(categories);
    }

    @Override
    public void onFailureResult_CAT(String errorMsg_cat) {
        view.showError(errorMsg_cat);
    }

    @Override
    public void onSuccessResult(Meal result) {

    }

    @Override
    public void onFailureResult(String errorMessage) {

    }


}