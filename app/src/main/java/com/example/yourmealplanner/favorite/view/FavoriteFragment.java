package com.example.yourmealplanner.favorite.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.favorite.presenter.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteView, OnFavClickRemoveListener {

    public static final String TAG = "";

    private RecyclerView recyclerView;
    private FavoriteAdapter mAdapter;
    private FavoritePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment, container, false);

        recyclerView = view.findViewById(R.id.favRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        presenter = new FavoritePresenter(this, requireActivity());

        mAdapter = new FavoriteAdapter(requireActivity(), new ArrayList<>(), this);
        recyclerView.setAdapter(mAdapter);

        presenter.loadFavorites();

        return view;
    }


    @Override
    public void showFavoriteProducts(List<Meal> meals) {
        mAdapter.setMeals(meals);
    }

    @Override
    public void showError(String errorMsg) {
        Log.e("FavoriteFragment", errorMsg);
    }



    @Override
    public void onFavMealClick(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.getIdMeal());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_meal, bundle);
    }

    @Override
    public void onFavoriteRemoved(Meal meal) {
        presenter.removeFavorite(meal);
    }
}
