package com.example.yourmealplanner.favorite.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.view.MealAdapter;
import com.example.yourmealplanner.R;

import java.util.ArrayList;
import java.util.List;
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final Context context;
    private List<Meal> values;
    private static final String TAG = "RecyclerView";
    private final OnFavClickRemoveListener listener;

    public FavoriteAdapter(Context context, List<Meal> values, OnFavClickRemoveListener listener) {
        this.context = context;
        this.values = values != null ? values : new ArrayList<>();
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView meal_image;
        public TextView meal_name;
        public ConstraintLayout constraintLayout;
        public View layout;
        public Button btnFav;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            meal_image = v.findViewById(R.id.img);
            meal_name = v.findViewById(R.id.titleTxt);
            btnFav = v.findViewById(R.id.btnRemoveFav);
            constraintLayout = v.findViewById(R.id.main);

            Log.d(TAG, "ViewHolder initialized: btnFav is " + (btnFav != null ? "not null" : "null"));

            meal_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onFavMealClick(values.get(getAdapterPosition()));
                    }
                }
            });


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.inside_fav_fragment, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Meal meal = values.get(position);
        String imageUrl = meal.getStrMealThumb();
        Log.d("FavoriteAdapter", "Meal: " + meal.getStrMeal() + ", Image URL: " + imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.meal_image);
        } else {
            holder.meal_image.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.meal_name.setText(meal.getStrMeal());

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    Meal mealToRemove = values.get(position);
                    listener.onFavoriteRemoved(mealToRemove);
                    updateMeals(values);
                } else {
                    Log.e("MealAdapter", "Listener is null");
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return values.size();
    }

    public void updateMeals(List<Meal> newMeals) {
        this.values.clear();
        this.values.addAll(newMeals);
        notifyDataSetChanged();
    }


    public void setMeals(List<Meal> meals) {
        this.values = meals != null ? meals : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void removeMeal(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }
}
