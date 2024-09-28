package com.example.yourmealplanner.Profile.view;

import com.example.yourmealplanner.Home.model.Meal;

public interface OnMealClickListener {
    void onMealClick(Meal meal);
    void onClearWeekDayClick(Meal meal);
}
