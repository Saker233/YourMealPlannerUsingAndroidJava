package com.example.yourmealplanner.Home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private OnCategoryClickListener listener;
    private static final String TAG = "RecyclerView";


    public CategoryAdapter(Context _context, List<Category> categories, OnCategoryClickListener listener) {
        this.categoryList = categories;
        this.listener = listener;
        this.context = _context;
    }
    public void setCategories(List<Category> categories) {
        this.categoryList = categories; // Update the category list
        notifyDataSetChanged(); // Notify adapter to refresh the data
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryList.get(position);
        // Bind category data to the views (e.g., set the name, image, etc.)
        holder.categoryName.setText(category.getStrCategory());

        // Use RequestOptions to ensure proper image loading
        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background)) // Add a placeholder image
                .into(holder.categoryImage);

        // Set the click listener in the ViewHolder
        holder.itemView.setOnClickListener(v -> {
            listener.onCategoryClick(category);
            Toast.makeText(context, "Clicked on " + category.getStrCategory(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.CategoryName);
            categoryImage = itemView.findViewById(R.id.CategoryImage);
        }
    }
}