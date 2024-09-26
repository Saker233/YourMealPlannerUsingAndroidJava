package com.example.yourmealplanner.favorite.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.database.AppDataBase;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;
import com.example.yourmealplanner.favorite.view.FavoriteView;

import java.util.List;

public class FavoritePresenter {
    private FavoriteView view;
    private MealDao mealDao;
    private MealsLocalDataSource local;

    public FavoritePresenter(FavoriteView view, Context context) {
        this.view = view;
        this.mealDao = AppDataBase.getInstance(context).getMealDao();
        this.local = MealsLocalDataSourceImp.getInstance(context);
    }

    public void removeProductFromFavorites(Meal meal) {
        if(meal != null) {
            local.deleteMeal(meal);
//            Toast.makeText((Context) view, "Product Removed To Favourites", Toast.LENGTH_SHORT).show();
        }


    }

    public void loadFavorites() {
        LiveData<List<Meal>> favorites = local.getFavorites();
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
        local.deleteMeal(meal);
        view.showError("Meal removed from favorites");
    }
}
