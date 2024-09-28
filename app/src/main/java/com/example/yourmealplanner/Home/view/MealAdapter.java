package com.example.yourmealplanner.Home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.R;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> implements Filterable {

    private List<Meal> meals;
    private List<Meal> mealsFull;
    private OnMealClickListener mealClickListener;

    public MealAdapter(List<Meal> meals, OnMealClickListener mealClickListener) {
        this.meals = meals;
        this.mealsFull = new ArrayList<>(meals);
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
    public void setMeals(List<Meal> meals) {
        this.meals.clear();
        this.meals.addAll(meals);

        this.mealsFull.clear();
        this.mealsFull.addAll(meals);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Meal> filteredMeals = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredMeals.addAll(mealsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Meal meal : mealsFull) {
                        if (meal.getStrMeal().toLowerCase().contains(filterPattern)) {
                            filteredMeals.add(meal);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredMeals;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                meals.clear();
                meals.addAll((List<Meal>) results.values);
                notifyDataSetChanged();
            }
        };
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