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

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.view.MealAdapter;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Search.model.Area;
import com.example.yourmealplanner.Search.presenter.SearchPresenter;

import java.util.List;

public class CountryFragment extends Fragment implements SearchViews, OnCountryClickListener {

    private RecyclerView recyclerViewCountries;
    private SearchPresenter searchPresenter;
    private CountryAdapter countryAdapter;
    private MealRemoteDataSource remote;
    private List<Meal> meals;
    private MealAdapter mealAdapter;
    private RecyclerView recyclerViewMeals;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewCountries = view.findViewById(R.id.insidecountryRecycler);
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(getContext()));

        countryAdapter = new CountryAdapter(this);
        recyclerViewCountries.setAdapter(countryAdapter);
        remote = MealRemoteDataSource.getInstance(getContext());
        if (remote == null) {
            Log.e("CountryFragment", "MealRemoteDataSource is null");
        }
        searchPresenter = new SearchPresenter(this, remote);
        searchPresenter.loadCountries();

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void displayCountries(List<Area> areas) {
        countryAdapter.setCountries(areas);
    }



    @Override
    public void displayMeals(List<Meal> meals) {

    }

    @Override
    public void displayError(String message) {
    }

    @Override
    public void onCountryClick(String countryName) {
        navigateToMealsFragment(countryName);

    }

    private void navigateToMealsFragment(String countryName) {
        Bundle bundle = new Bundle();
        bundle.putString("countryName", countryName);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_country_search_meals, bundle);
    }


}
