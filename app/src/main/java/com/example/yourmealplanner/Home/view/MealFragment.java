package com.example.yourmealplanner.Home.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.yourmealplanner.Home.model.Meal;
import com.example.yourmealplanner.Home.presenter.MealPresenter;
import com.example.yourmealplanner.Home.presenter.MealPresenterImp;
import com.example.yourmealplanner.Network.MealRemoteDataSource;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.database.AppDataBase;
import com.example.yourmealplanner.database.MealDao;
import com.example.yourmealplanner.database.MealsLocalDataSourceImp;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MealFragment extends Fragment implements MealView {

    private TextView mealName;
    private ImageView mealImage;
    private TextView txtIngredient;
    private Button btnFav;
    private Button btnWeek;
    private WebView meal_video;
    private MealPresenter mealPresenter;
    private static final String TAG = "MealFragment";
    private MealRemoteDataSource remoteDataSource;
    private MealsLocalDataSourceImp local;
    private Meal currentMeal;
    private List<Meal> favoriteMeals;
    private TextView txtSteps;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDataBase db = AppDataBase.getInstance(getContext());
        MealDao mealDao = db.getMealDao();
        remoteDataSource = MealRemoteDataSource.getInstance(getContext());
        local =  MealsLocalDataSourceImp.getInstance(getContext());

        mealPresenter = new MealPresenterImp(this, local, remoteDataSource);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.inside_meal_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealName = view.findViewById(R.id.meal_name);
        mealImage = view.findViewById(R.id.meal_image);
        txtIngredient = view.findViewById(R.id.txtIngredient);
        btnFav = view.findViewById(R.id.btnFav);
        btnWeek = view.findViewById(R.id.btnWeek);
        meal_video = view.findViewById(R.id.webView);
        txtSteps =  view.findViewById(R.id.txtSteps);

        if (getArguments() != null && currentMeal == null) {
            String mealId = getArguments().getString("mealId");
            mealPresenter.getMealDetails(mealId);
        }

        mealPresenter.getFavorites().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favoriteMeals = meals;
                if (currentMeal != null) {
//                    btnFav.setText(isMealFavorite(currentMeal) ? "Remove from Favorites" : "Add to Favorites");
                }
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMeal != null) {


                        mealPresenter.addToFav(currentMeal);
//                        btnFav.setText("Remove from Favorites");
                        Toast.makeText(view.getContext(), "Meal added to favorites", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(view.getContext(), "No meal information available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnWeek.setOnClickListener(view1 -> {
            if (currentMeal != null) {
                showCalendarDialog();
                Toast.makeText(getContext(), "Meal details LOADED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Meal details not loaded yet", Toast.LENGTH_SHORT).show();
            }
        });

    }




    private void showMealSelectionDialog() {
        String[] weekdays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a weekday")
                .setItems(weekdays, (dialog, which) -> {
                    String selectedWeekday = weekdays[which];
                    if (currentMeal != null) {
                        mealPresenter.assignMealToWeekday(currentMeal, selectedWeekday);
                        Toast.makeText(getContext(), currentMeal.getStrMeal() + " assigned to " + selectedWeekday, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showCalendarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_calendar, null);
        CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        Spinner mealTypeSpinner = dialogView.findViewById(R.id.mealTypeSpinner);

        long todayInMillis = System.currentTimeMillis();

        calendarView.setMinDate(todayInMillis);

        final long[] selectedDate = new long[1];

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            selectedDate[0] = calendar.getTimeInMillis();
            Log.d("CalendarSelection", "Date selected: " + selectedDate[0]);
        });

        builder.setView(dialogView)
                .setTitle("Select a date and meal type")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (currentMeal != null && selectedDate[0] != 0) {
                        Date selected = new Date(selectedDate[0]);
                        String selectedMealType = mealTypeSpinner.getSelectedItem().toString();
                        Meal.MealType mealType;

                        switch (selectedMealType) {
                            case "Main Dish":
                                mealType = Meal.MealType.MAIN_DISH;
                                break;
                            case "Side Dish":
                                mealType = Meal.MealType.SIDE_DISH;
                                break;
                            case "Dessert":
                                mealType = Meal.MealType.DESSERT;
                                break;
                            default:
                                mealType = Meal.MealType.MAIN_DISH;
                                break;
                        }

                        currentMeal.setMealType(mealType);
                        mealPresenter.assignMealToDate(currentMeal, selected);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedDate = sdf.format(selected);
                        Toast.makeText(getContext(), currentMeal.getStrMeal() + " assigned as " + mealType.name() + " to " + formattedDate, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please select a valid date", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void showDetails(Meal meal) {
        currentMeal = meal;
        mealName.setText(meal.getStrMeal());
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);
//        txtIngredient.setText(meal.getStrIngredients());
        txtSteps.setText((meal.getStrInstructions()));

        StringBuilder ingredientsWithMeasurements = new StringBuilder();
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient1(), meal.getStrMeasure1());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient2(), meal.getStrMeasure2());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient3(), meal.getStrMeasure3());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient4(), meal.getStrMeasure4());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient5(), meal.getStrMeasure5());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient6(), meal.getStrMeasure6());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient7(), meal.getStrMeasure7());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient8(), meal.getStrMeasure8());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient9(), meal.getStrMeasure9());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient10(), meal.getStrMeasure10());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient11(), meal.getStrMeasure11());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient12(), meal.getStrMeasure12());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient13(), meal.getStrMeasure13());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient14(), meal.getStrMeasure14());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient15(), meal.getStrMeasure15());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient16(), meal.getStrMeasure16());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient17(), meal.getStrMeasure17());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient18(), meal.getStrMeasure18());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient19(), meal.getStrMeasure19());
        addIngredientToList(ingredientsWithMeasurements, meal.getStrIngredient20(), meal.getStrMeasure20());

        txtIngredient.setText(ingredientsWithMeasurements.toString());

        meal_video.setVisibility(View.VISIBLE);
        String videoUrl = meal.getStrYoutube().replace("watch?v=", "embed/");
        String videoFrame = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
        meal_video.getSettings().setJavaScriptEnabled(true);
        meal_video.loadData(videoFrame, "text/html", "utf-8");
    }

    private void addIngredientToList(StringBuilder sb, String ingredient, String measure) {
        if (ingredient != null && !ingredient.isEmpty() && !ingredient.equals("null")) {
            sb.append(measure != null && !measure.isEmpty() && !measure.equals("null") ? measure : "")
                    .append(" ")
                    .append(ingredient)
                    .append("\n");
        }
    }

    @Override
    public void showErrorMsg(String error) {
        Log.e(TAG, error);
    }

    private boolean isMealFavorite(Meal meal) {
        if (favoriteMeals != null) {
            for (Meal favorite : favoriteMeals) {
                if (favorite.getIdMeal().equals(meal.getIdMeal())) {
                    return true;
                }
            }
        }
        return false;
    }
}