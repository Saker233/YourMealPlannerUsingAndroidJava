package com.example.yourmealplanner.Search.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.model.Ingredient;
import com.example.yourmealplanner.Search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment implements SearchViews, OnIngredientClickListener {

    private RecyclerView recyclerViewIngredients;
    private IngredientAdapter ingredientAdapter;
    private SearchPresenter searchPresenter;
    private MealRemoteDataSource remote;
    private OnIngredientClickListener ingredientClickListener;
    private List<Ingredient> ingredients = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewIngredients = view.findViewById(R.id.recyclerIngredients);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));

        ingredientAdapter = new IngredientAdapter(ingredients, this);
        recyclerViewIngredients.setAdapter(ingredientAdapter);

        remote = MealRemoteDataSource.getInstance(getContext());
        if (remote == null) {
            Log.e("IngredientsFragment", "MealRemoteDataSource is null");
        }

        searchPresenter = new SearchPresenter(this, remote);
        searchPresenter.loadIngredients();

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ingredientAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }



    @Override
    public void displayCountries(List<Area> areas) {

    }

    @Override
    public void displayMeals(List<Meal> meals) {

    }

    @Override
    public void displayError(String message) {
        // Handle error
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        ingredientAdapter.updateIngredients(ingredients);
    }

    @Override
    public void onIngredientClick(String ingredientName) {
        navigateToMealsFragment(ingredientName);
    }

    private void navigateToMealsFragment(String ingredientName) {
        Bundle bundle = new Bundle();
        bundle.putString("ingredientName", ingredientName);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_ingredient_meals, bundle);
    }
}
