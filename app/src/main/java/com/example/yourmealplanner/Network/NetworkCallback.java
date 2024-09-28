package com.example.yourmealplanner.Network;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public interface NetworkCallback<T> {
    void onSuccessResult_MEAL(Meal meal);
    void onFailureResult_MEAL(String errorMsg_meal);

    void onSuccessResult_CAT(List<Category> categories);
    void onFailureResult_CAT(String errorMsg_cat);
    void onSuccessResult(T result);
    void onFailureResult(String errorMessage);
}
