package com.example.yourmealplanner.Home.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.presenter.HomePresenter;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.R;
//import com.example.yourmealplanner.Repo.RepoImp;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements  HomeView, OnCategoryClickListener, OnMealClickListener {

    private TextView txtInsp;
    private ImageView imageInsp;
    private RecyclerView recyclerCategories;
    private CategoryAdapter categoryAdapter;
    private HomePresenter presenter;
    private Meal randomMeal;
    private boolean isMealFetched = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(requireContext(), this,
                MealsLocalDataSourceImp.getInstance(requireContext()),
                MealRemoteDataSource.getInstance(requireContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtInsp = view.findViewById(R.id.txtInsp);
        imageInsp = view.findViewById(R.id.imageInsp);
        recyclerCategories = view.findViewById(R.id.recyclerCategories);
        recyclerCategories.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerCategories.setLayoutManager(linearLayoutManager);

        categoryAdapter = new CategoryAdapter(requireContext(), new ArrayList<Category>(), this);
        recyclerCategories.setAdapter(categoryAdapter);

        presenter.getRandomMeal();
        presenter.getCategories();


        view.findViewById(R.id.btnViewDet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomMeal != null) {
                    navigateToMealDetails(randomMeal);
                } else {
                    Toast.makeText(getContext(), "No meal selected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    @Override
    public void displayRandomMeal(Meal meal) {
        this.randomMeal = meal;
        if (meal != null) {
            txtInsp.setText(meal.getStrMeal());

            // Load the image using Glide
            Glide.with(requireContext())
                    .load(meal.getStrMealThumb())
                    .apply(new RequestOptions().override(200, 200))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageInsp);

            Log.i(TAG, "displayRandomMeal: Meal displayed successfully");
        } else {
            // Handle null meal case
            txtInsp.setText("No data available");
            imageInsp.setImageResource(R.drawable.food);
            Log.i(TAG, "displayRandomMeal: No data available");
        }
    }

    @Override
    public void showError(String errorMessage) {
        Log.e("HomeFragment", errorMessage);
    }

    @Override
    public void displayCategories(List<Category> categories) {
        categoryAdapter.setCategories(categories);
    }

    @Override
    public void onCategoryClick(Category category) {
        navigateToCategoryDetails(category);
    }

    @Override
    public void onMealClick(Meal meal) {
        navigateToMealDetails(meal);
    }

    private void navigateToMealDetails(Meal meal) {
        // Create a Bundle to pass the meal ID
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.getIdMeal()); // Ensure you pass the meal ID

        // Use NavController to navigate
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_meal, bundle); // Use the correct ID from your nav_graph.xml
    }



    private void navigateToCategoryDetails(Category category) {
        CategoryDetailsFragment fragment = new CategoryDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category.getStrCategory());
        bundle.putString("categoryImage", category.getStrCategoryThumb());
        fragment.setArguments(bundle);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_category_details, bundle);
    }
}
