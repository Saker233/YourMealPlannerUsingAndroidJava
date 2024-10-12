package com.example.yourmealplanner.favorite.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Repo.RepositoryImpl;
import com.example.yourmealplanner.database.AppDataBase;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;
import com.example.yourmealplanner.favorite.view.FavoriteView;

import java.util.List;

public class FavoritePresenter {
    private FavoriteView view;
    private MealDao mealDao;
    private MealsLocalDataSource repo;
    private RepositoryImpl repository;
    private Context context;

    public FavoritePresenter(FavoriteView view, Context context) {
        this.view = view;
        this.mealDao = AppDataBase.getInstance(context).getMealDao();
        this.repo = MealsLocalDataSourceImp.getInstance(context);
        this.context = context;
        this.repository = RepositoryImpl.getInstance(context);


    }

//    public void removeProductFromFavorites(Meal meal) {
//        if(meal != null) {
//            repository.deleteMeal(meal);
//        }
//
//
//    }

    public void loadFavorites() {
        String userId = getUserId();
        if (userId == null) {
            LiveData<List<Meal>> favorites = repo.getFavorites();
            favorites.observeForever(new Observer<List<Meal>>() {
                @Override
                public void onChanged(List<Meal> meals) {
                    if (meals != null && !meals.isEmpty()) {
                        view.showFavoriteProducts(meals);
                    } else {
                        view.showError("No favorite meals found");
                    }
                }
            });

            return;
        }
        LiveData<List<Meal>> favorites = repo.getFavoriteMealsForUser(userId);
        favorites.observeForever(new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals != null && !meals.isEmpty()) {
                    view.showFavoriteProducts(meals);
                } else {
                    view.showError("No favorite meals found");
                }
            }
        });
    }

    public void removeFavorite(Meal meal) {
        repository.removeFromFavorite(meal);
        view.showError("Meal removed from favorites");
    }


    private String getUserId() {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return preferences.getString("userId", null);
    }
}
