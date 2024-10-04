package com.example.yourmealplanner.Profile.view;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.example.yourmealplanner.database.MealsLocalDataSource;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment implements ProfileView, OnMealClickListener, OnLogOutClick {
    private RecyclerView weekRecyclerView;
    private DateMealAdapter adapter;
    private List<Meal> mealsOfTheWeek;
    private ImageButton btnLogOut;
    private ProfilePresenter presenter;
    private MealDao mealDao;
    private MealsLocalDataSourceImp local;
    private CalendarView calendarView;
    private String selectedDate;
    private Button btnShowShoppingList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        weekRecyclerView = view.findViewById(R.id.weekRecycler);
        calendarView = view.findViewById(R.id.calendarView);
        btnShowShoppingList = view.findViewById(R.id.btnShowShoppingList);

        weekRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mealsOfTheWeek = new ArrayList<>();
        adapter = new DateMealAdapter(new ArrayList<>(), this);
        weekRecyclerView.setAdapter(adapter);

        mealDao = AppDataBase.getInstance(requireContext()).getMealDao();
        local = MealsLocalDataSourceImp.getInstance(requireContext());
        presenter = new ProfilePresenter(this, mealDao, local, requireContext(), this, this);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                String userId = getUserId();
                if (userId == null) {
                    presenter.getMealsForDate(selectedDate);

                } else {
                    presenter.getMealsForDateAndUser(selectedDate, userId);
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.logout();

            }
        });

        btnShowShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.showShoppingList(getUserId());
            }
        });

        return view;
    }





    @Override
    public void showMealsForDate(List<Meal> meals) {
        StringBuilder mealsDisplay = new StringBuilder();
        for (Meal meal : meals) {
            mealsDisplay.append(meal.getStrMeal()).append(" (").append(meal.getMealType().name()).append(")\n");
        }
        adapter.updateMeals(meals);
    }

    @Override
    public void showNoMealsMessage() {
        Toast.makeText(getContext(), "No meals found for this date", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayAggregatedIngredients(Map<String, Integer> ingredientsMap) {
        showAlertDialog(ingredientsMap);
    }

    @Override
    public void displayFavoriteMeals(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            Toast.makeText(getContext(), "No favorite meals found", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter.updateMeals(meals);

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
    public void onMealDelete(Meal meal) {
        if (meal.isFav()) {
            meal.setFav(false);

            if (meal.hasAssignedDate()) {
                local.clearAssignedDate(meal);
            }


        } else {
            if (meal.hasAssignedDate()) {
                local.clearAssignedDate(meal);
            } else {
                local.deleteMeal(meal);
            }
        }

//        presenter.getMealsForDate(selectedDate);
    }



    @Override
    public void onLogOut() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();

    }




    private void showAlertDialog(Map<String, Integer> ingredientMap) {
        StringBuilder message = new StringBuilder("Shopping List:\n");

        for (Map.Entry<String, Integer> entry : ingredientMap.entrySet()) {
            message.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Shopping List")
                .setMessage(message.toString())
                .setPositiveButton("OK", null)
                .create();


        alertDialog.show();
    }


    private String getUserId() {
        SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE);
        return preferences.getString("userId", null);
    }



}
