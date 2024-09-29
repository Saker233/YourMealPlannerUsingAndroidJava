package com.example.yourmealplanner.Profile.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourmealplanner.Authentecation.view.LoginActivity;
import com.example.yourmealplanner.Home.view.MealFragment;
import com.example.yourmealplanner.Profile.presenter.ProfilePresenter;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.database.AppDataBase;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements ProfileView, OnMealClickListener, OnLogOutClick {
    private RecyclerView weekRecyclerView;
    private WeekMealAdapter adapter;
    private List<Meal> mealsOfTheWeek;
    private ImageButton btnLogOut;
    private ProfilePresenter presenter;
    private MealDao mealDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        weekRecyclerView = view.findViewById(R.id.weekRecycler);
        weekRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mealsOfTheWeek = new ArrayList<>();

        adapter = new WeekMealAdapter(mealsOfTheWeek, this);
        weekRecyclerView.setAdapter(adapter);

        mealDao = AppDataBase.getInstance(requireContext()).getMealDao();
        presenter = new ProfilePresenter(this, mealDao, requireContext(), this, this);


        presenter.getMealsForWeek();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.logout();

            }
        });

        return view;
    }



    @Override
    public void showMealsForWeek(List<Meal> meals) {
//        WeekMealAdapter adapter = new WeekMealAdapter(meals, this);
//        weekRecyclerView.setAdapter(adapter);
        mealsOfTheWeek.clear();
        mealsOfTheWeek.addAll(meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
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
    public void onClearWeekDayClick(Meal meal) {
        presenter.removeMealFromWeek(meal);
    }

    @Override
    public void onLogOut() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();

    }
}
