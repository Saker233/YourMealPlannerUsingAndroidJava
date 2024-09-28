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

public class WeekMealAdapter extends RecyclerView.Adapter<WeekMealAdapter.WeekMealViewHolder> {

    private List<Meal> meals;
    private OnMealClickListener mealClickListener;


    public WeekMealAdapter(List<Meal> meals, OnMealClickListener mealClickListener) {
        this.meals = meals;
        this.mealClickListener = mealClickListener;
    }

    @NonNull
    @Override
    public WeekMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent, false);
        return new WeekMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekMealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.dayName.setText(meal.getWeekday());
        holder.mealDayName.setText(meal.getStrMeal());


        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .override(600, 400)
                .into(holder.dayImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mealClickListener != null) {
                    mealClickListener.onMealClick(meal);
                }
            }
        });

        holder.btnClearWeekDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mealClickListener != null) {
                    mealClickListener.onClearWeekDayClick(meal);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class WeekMealViewHolder extends RecyclerView.ViewHolder {
        TextView dayName, mealDayName;
        ImageView dayImage;
        Button btnClearWeekDay;
        public WeekMealViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            mealDayName = itemView.findViewById(R.id.mealDayName);
            dayImage = itemView.findViewById(R.id.dayImage);
            btnClearWeekDay =  itemView.findViewById(R.id.btnClearWeekDay);
        }
    }
}
