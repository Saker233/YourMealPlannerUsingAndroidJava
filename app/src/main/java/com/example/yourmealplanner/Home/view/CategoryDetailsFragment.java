package com.example.yourmealplanner.Home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.model.CategoryClient;
import com.example.yourmealplanner.Home.model.MealClient;
import com.example.yourmealplanner.Network.MealCallback;
import com.example.yourmealplanner.Network.NetworkCallback;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Home.model.Meal;

import java.util.List;

public class CategoryDetailsFragment extends Fragment implements OnMealClickListener, OnCategoryClickListener {

    private RecyclerView catRecycle;
    private MealAdapter mealAdapter;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inside_category_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCategoriesFromDataSource();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        catRecycle = view.findViewById(R.id.catRecycle);
        catRecycle.setLayoutManager(new LinearLayoutManager(getContext()));


        Bundle args = getArguments();
        if (args != null) {
            String categoryName = args.getString("categoryName");
            String categoryImage = args.getString("categoryImage");

            fetchMealsByCategory(categoryName);
        }
    }

    private void fetchMealsByCategory(String categoryName) {
        MealClient.getInstance().getMealsByCategory(categoryName, new MealCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meals) {
                mealAdapter = new MealAdapter(meals, CategoryDetailsFragment.this);

                catRecycle.setAdapter(mealAdapter);
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void getCategoriesFromDataSource() {
        CategoryClient.getInstance().getCategories(new NetworkCallback<List<Category>>() {
            @Override
            public void onSuccessResult_CAT(List<Category> categories) {
                categoryAdapter = new CategoryAdapter(getContext(), categories, CategoryDetailsFragment.this);
                catRecycle.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailureResult_CAT(String errorMsg_cat) {
            }

            @Override
            public void onSuccessResult(List<Category> result) {

            }

            @Override
            public void onFailureResult(String errorMessage) {

            }

            @Override
            public void onSuccessResult_MEAL(Meal meals) {
            }

            @Override
            public void onFailureResult_MEAL(String errorMsg_meal) {
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

    @Override
    public void onCategoryClick(Category category) {

    }
}