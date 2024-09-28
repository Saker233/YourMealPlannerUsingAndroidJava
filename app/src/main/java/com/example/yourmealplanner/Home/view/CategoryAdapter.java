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
        this.categoryList = categories;
        notifyDataSetChanged();
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
        holder.categoryName.setText(category.getStrCategory());

        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryClick(category);
                Toast.makeText(context, "Clicked on " + category.getStrCategory(), Toast.LENGTH_SHORT).show();
            }
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
            categoryName = itemView.findViewById(R.id.dayName);
            categoryImage = itemView.findViewById(R.id.dayImage);
        }
    }
}