package com.example.yourmealplanner.favorite.view;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface FavoriteView {
    void showFavoriteProducts(List<Meal> products);
    void showError(String errorMsg);
}
