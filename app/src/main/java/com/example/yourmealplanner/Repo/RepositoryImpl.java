package com.example.yourmealplanner.Repo;

import androidx.lifecycle.LiveData;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.List;

public class RepositoryImpl implements Repository {

    private final MealRemoteDataSource remoteDataSource;
    private final MealsLocalDataSource localDataSource;
    private static RepositoryImpl repo = null;

    public RepositoryImpl(MealRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static RepositoryImpl getInstance(MealRemoteDataSource remoteDataSource, MealsLocalDataSourceImp localDataSource){
        if(repo == null){
            repo = new RepositoryImpl(remoteDataSource, localDataSource);
        }
        return repo;
    }

    @Override
    public void getRandomMeal(NetworkCallback<Meal> callback) {
        remoteDataSource.getRandom(new NetworkCallback<Meal>() {
            @Override
            public void onSuccessResult_MEAL(Meal meal) {
                callback.onSuccessResult(meal);
            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {
                callback.onFailureResult_MEAL(errorMsg_meal);
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

    @Override
    public void getMealDetails(String mealId, NetworkCallback<Meal> callback) {
        remoteDataSource.getDetails(mealId, new NetworkCallback<Meal>() {
            @Override
            public void onSuccessResult_MEAL(Meal meal) {
                callback.onSuccessResult(meal);
            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {
                callback.onFailureResult_MEAL(errorMsg_meal);
            }

            @Override
            public void onSuccessResult_CAT(List<Category> categories) {
                // Not applicable for this method
            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {
                // Not applicable for this method
            }

            @Override
            public void onSuccessResult(Meal result) {

            }

            @Override
            public void onFailureResult(String errorMessage) {

            }


        });
    }

    @Override
    public void getMealsByCategory(String categoryName, NetworkCallback<List<Meal>> callback) {
        remoteDataSource.getMealsByCategory(categoryName, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccessResult(List<Meal> meals) {
                callback.onSuccessResult(meals);
            }

            @Override
            public void onFailureResult(String errorMessage) {

            }


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
                callback.onFailureResult_CAT(errorMsg_cat);
            }
        });
    }

    @Override
    public void insertMeal(Meal meal) {
        localDataSource.insertMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getFavorites() {
        return localDataSource.getFavorites();
    }

    @Override
    public void changeFavoriteState(Meal meal) {
        localDataSource.changeFavoriteState(meal);
    }
}
