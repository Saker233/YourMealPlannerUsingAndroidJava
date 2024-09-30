package com.example.yourmealplanner.Search.view;

import android.util.Log;
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
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.model.Ingredient;

import java.util.ArrayList;
import java.util.List;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements Filterable {

    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Ingredient> filteredIngredients;
    private OnIngredientClickListener listener;



    public IngredientAdapter(List<Ingredient> ingredients, OnIngredientClickListener listener) {
        this.ingredients = ingredients;
        this.filteredIngredients = new ArrayList<>(ingredients);
        this.listener = listener;
    }


    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = filteredIngredients.get(position);
        holder.txtIngredientName.setText(ingredient.getIngredientName());

        Glide.with(holder.itemView.getContext())
                .load(ingredient.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgIngredient);

        holder.itemView.setOnClickListener(v -> {
            if (listener  != null) {
                listener .onIngredientClick(ingredient.getIngredientName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredIngredients.size();
    }


    public void updateIngredients(List<Ingredient> newIngredients) {
        this.ingredients.clear();
        this.ingredients.addAll(newIngredients);

        filteredIngredients.clear();
        filteredIngredients.addAll(newIngredients);
        notifyDataSetChanged();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
        this.filteredIngredients.clear();
        this.filteredIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                List<Ingredient> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(ingredients);
                } else {
                    for (Ingredient ingredient : ingredients) {
                        if (ingredient.getIngredientName().toLowerCase().contains(query)) {
                            filteredList.add(ingredient);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                Log.d("IngredientAdapter", "Filtered results. Count: " + results.count);
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredIngredients.clear();
                if (results.values != null) {
                    filteredIngredients.addAll((List<Ingredient>) results.values);
                }
                Log.d("IngredientAdapter", "Published results. Filtered count: " + filteredIngredients.size());
                notifyDataSetChanged();
            }
        };
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName;
        ImageView imgIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIngredientName = itemView.findViewById(R.id.txtIngredientName);
            imgIngredient = itemView.findViewById(R.id.imgIngredient);
        }
    }
}
