package com.example.yourmealplanner.Search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yourmealplanner.Home.model.Category;
import com.example.yourmealplanner.Home.view.CategoryAdapter;
import com.example.yourmealplanner.R;
import com.example.yourmealplanner.favorite.presenter.FavoritePresenter;
import com.example.yourmealplanner.favorite.view.FavoriteAdapter;

import java.util.ArrayList;

public class SeachFragment extends Fragment {

    public static final String TAG = "";

    private Button btnSearchCountry;
    private Button btnSearchIngredient;
    private Button btnSearchCategory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSearchCountry = view.findViewById(R.id.btnSearchCountry);
        btnSearchIngredient = view.findViewById(R.id.btnSearchIngredient);
        btnSearchCategory = view.findViewById(R.id.btnSearchCategory);


        btnSearchCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_country_search, null);
            }
        });

        btnSearchIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_ingredient, null);
            }
        });

        btnSearchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_home, null);
            }
        });
    }


}
