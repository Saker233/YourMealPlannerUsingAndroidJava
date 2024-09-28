package com.example.yourmealplanner.Search.presenter;

import static java.security.AccessController.getContext;

import android.widget.SearchView;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.view.SearchViews;

import java.util.List;

public class SearchPresenter {
    private SearchViews searchView;
    private MealRemoteDataSource mealRemoteDataSource;

    public SearchPresenter(SearchViews searchView, MealRemoteDataSource mealRemoteDataSource) {
        this.searchView = searchView;
        this.mealRemoteDataSource = mealRemoteDataSource; // Ensure this is assigned correctly
    }

    public void loadCountries() {
        mealRemoteDataSource.getAreas(new NetworkCallback<List<Area>>() {  // Change to List<Area>
            @Override
            public void onSuccessResult_MEAL(Meal meal) {
            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {
            }

            @Override
            public void onSuccessResult_CAT(List<Category> categories) {
            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {
            }

            @Override
            public void onSuccessResult(List<Area> areas) {
                searchView.displayCountries(areas);
            }

            @Override
            public void onFailureResult(String errorMessage) {

            }


        });
    }

    public void loadMealsByCountry(String countryName) {
        mealRemoteDataSource.fetchMeals(countryName, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccessResult_MEAL(Meal meal) {

            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {

            }

            @Override
            public void onSuccessResult_CAT(List<Category> categories) {

            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {

            }

            @Override
            public void onSuccessResult(List<Meal> meals) {
                searchView.displayMeals(meals);
            }

            @Override
            public void onFailureResult(String errorMessage) {
                searchView.displayError(errorMessage);
            }
        });
    }
}
