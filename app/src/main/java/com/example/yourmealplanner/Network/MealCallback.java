package com.example.yourmealplanner.Network;

import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface MealCallback<T> {
    void onSuccess(T result);
    void onError(Throwable throwable);
}
