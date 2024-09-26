package com.example.yourmealplanner.favorite.view;

import com.example.yourmealplanner.Home.model.Meal;

public interface OnFavClickRemoveListener {
    void onFavMealClick(Meal meal);
    void onFavoriteRemoved(Meal meal);
}
