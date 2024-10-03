package com.example.yourmealplanner.Profile.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.R;

import java.util.List;

public class DateMealAdapter extends RecyclerView.Adapter<DateMealAdapter.DateMealViewHolder> {

    private List<Meal> meals;
    private OnMealClickListener mealClickListener;

    public DateMealAdapter(List<Meal> meals, OnMealClickListener mealClickListener) {
        this.meals = meals;
        this.mealClickListener = mealClickListener;
    }

    @NonNull
    @Override
    public DateMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);
        return new DateMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateMealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealDate.setText(meal.getAssignedDate());
        holder.mealType.setText(meal.getMealType().toString());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .override(600, 400)
                .into(holder.mealImage);

        holder.btnClearMeal.setVisibility(View.VISIBLE);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mealClickListener != null) {
                    mealClickListener.onMealClick(meal);
                }
            }
        });

        holder.btnClearMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealClickListener.onMealDelete(meal);
            }
        });


    }

    public void updateMeals(List<Meal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class DateMealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, mealDate;
        ImageView mealImage;
        Button btnClearMeal;
        TextView mealType;

        public DateMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealName);
            mealDate = itemView.findViewById(R.id.mealDate);
            mealImage = itemView.findViewById(R.id.mealImage);
            btnClearMeal = itemView.findViewById(R.id.btnClearMeal);
            mealType = itemView.findViewById(R.id.mealType);
        }
    }
}
