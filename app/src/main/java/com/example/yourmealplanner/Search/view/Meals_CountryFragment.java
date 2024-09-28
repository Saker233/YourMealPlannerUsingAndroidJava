package com.example.yourmealplanner.Search.view;

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

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.view.MealAdapter;
import com.example.yourmealplanner.Home.view.MealFragment;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class Meals_CountryFragment extends Fragment implements SearchViews, OnMealClickListener {

    private RecyclerView recyclerCountry;
    private MealAdapter mealAdapter;
    private SearchPresenter searchPresenter;
    private MealRemoteDataSource mealRemoteDataSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inside_search_country, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerCountry = view.findViewById(R.id.recyclerCountry);
        recyclerCountry.setLayoutManager(new LinearLayoutManager(getContext()));

        mealAdapter = new MealAdapter(new ArrayList<>(), meal -> {

            onMealClick(meal);
        });
        recyclerCountry.setAdapter(mealAdapter);

        mealRemoteDataSource = MealRemoteDataSource.getInstance(getContext());

        if (getArguments() != null) {
            String countryName = getArguments().getString("countryName");
            Log.d("Meals_CountryFragment", "Country name received: " + countryName);
            fetchMeals(countryName);
        }

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mealAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void loadMealsByCountry(String countryName) {
        MealRemoteDataSource remote = MealRemoteDataSource.getInstance(getContext());
        searchPresenter = new SearchPresenter(this, remote);
        searchPresenter.loadMealsByCountry(countryName);
    }

    @Override
    public void displayCountries(List<Area> areas) {

    }

    @Override
    public void displayMeals(List<Meal> meals) {
        mealAdapter.setMeals(meals);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {
        Log.e("Meals_CountryFragment", "Error: " + message);
    }

    private void fetchMeals(String countryName) {
        mealRemoteDataSource.fetchMeals(countryName, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccessResult_MEAL(Meal meal) {

            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {

            }

            @Override
            public void onSuccessResult_CAT(List<Category> categories) {

            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {

            }

            @Override
            public void onSuccessResult(List<Meal> meals) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        mealAdapter.setMeals(meals);
                        mealAdapter.notifyDataSetChanged();
                        Log.d("Meals_CountryFragment", "Meals fetched successfully");
                    });
                }
            }


            @Override
            public void onFailureResult(String message) {
                Log.e("Meals_CountryFragment", "Error fetching meals: " + message);
            }
        });
    }

    @Override
    public void onMealClick(Meal meal) {
        MealFragment mealDetailsFragment = new MealFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.getIdMeal());
        mealDetailsFragment.setArguments(bundle);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_meal, bundle);
    }


}
