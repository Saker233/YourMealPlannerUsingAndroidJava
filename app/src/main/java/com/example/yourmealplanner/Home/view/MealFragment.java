package com.example.yourmealplanner.Home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDataBase db = AppDataBase.getInstance(getContext());
        MealDao mealDao = db.getMealDao();
        remoteDataSource = MealRemoteDataSource.getInstance(getContext());
        local = new MealsLocalDataSourceImp(getContext());

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

        if (getArguments() != null) {
            String mealId = getArguments().getString("mealId");
            mealPresenter.getMealDetails(mealId);
        }

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    @Override
    public void showDetails(Meal meal) {
        mealName.setText(meal.getStrMeal());
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);
        txtIngredient.setText(meal.getStrIngredients());
        if (meal.getStrYoutube() != null) {
            meal_video.setVisibility(View.VISIBLE);
            String videoUrl = meal.getStrYoutube().replace("watch?v=", "embed/");
            String videoFrame = "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";

            meal_video.getSettings().setJavaScriptEnabled(true);
            meal_video.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {

                }
            });
            meal_video.loadData(videoFrame, "text/html", "utf-8");
        } else {
            meal_video.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMsg(String error) {
        Log.e(TAG, error); // Log error messages
    }
}

