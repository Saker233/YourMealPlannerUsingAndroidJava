package com.example.yourmealplanner.Home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private OnMealClickListener mealClickListener;

    // Constructor
    public MealAdapter(List<Meal> meals, OnMealClickListener mealClickListener) {
        this.meals = meals;
        this.mealClickListener = mealClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.meal_image);
            mealName = itemView.findViewById(R.id.meal_name);

            itemView.setOnClickListener(v -> {
                if (mealClickListener != null) {
                    mealClickListener.onMealClick(meals.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Meal meal) {
            mealName.setText(meal.getStrMeal());
            Glide.with(itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .override(600, 400)
                    .into(mealImage);
        }
    }
}