//package com.example.yourmealplanner.Repo;
//
//import android.util.Log;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;
//
//import com.example.yourmealplanner.Home.model.Meal;
//import com.example.yourmealplanner.Home.view.MealView;
//import com.example.yourmealplanner.Network.MealCallback;
//import com.example.yourmealplanner.Network.MealRemoteDataSource;
//import com.example.yourmealplanner.Network.MealResponse;
//import com.example.yourmealplanner.Network.MealService;
//import com.example.yourmealplanner.database.MealsLocalDataSource;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RepoImp implements Repo {
//
//    private static final String TAG = "RepoImp";
//
//
//    MealsLocalDataSource mealsLocalDataSource;
//    MealRemoteDataSource remoteDataSource;
//
//    MealService mealService;
//    private static RepoImp mealsRepository;
//    MealView mealView;
//
//    public RepoImp(MealsLocalDataSource mealsLocalDataSource, MealRemoteDataSource remoteDataSource) {
//
//        this.mealsLocalDataSource = mealsLocalDataSource;
//        this.remoteDataSource = remoteDataSource;
//
//    }
//
//    public static RepoImp getInstance(MealsLocalDataSource mealsLocalDataSource, MealRemoteDataSource remoteDataSource) {
//        if (mealsRepository == null) {
//            mealsRepository = new RepoImp(mealsLocalDataSource, remoteDataSource);
//
//        }
//
//        return mealsRepository;
//    }
//
//
//    @Override
//    public void changeFavoriteState(Meal meal) {
//        mealsLocalDataSource.changeFavoriteState(meal);
//    }
//
//
//    @Override
//    public void deleteMeal(Meal meal) {
//        mealsLocalDataSource.deleteMeal(meal);
//    }
//
//    @Override
//    public LiveData<List<Meal>> getDailyMeal() {
//        return null;
//    }
//
//    @Override
//    public LiveData<List<Meal>> getFavorites() {
//        return mealsLocalDataSource.getFavorites();
//    }
//
//    @Override
//    public Call<MealResponse> getSearchByName(String query) {
//        return null;
//    }
//
//    @Override
//    public LiveData<Meal> getSingleDetailMeals(String id) {
//        MutableLiveData<Meal> mealLiveData = new MutableLiveData<>();
//
//        // Fetch meal details from remote source
//        remoteDataSource.getMealDetails(id, new MealCallback<MealResponse>() {
//            @Override
//            public void onSuccess(MealResponse response) {
//                if (response != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
//                    Meal detailedMeal = response.getMeals().get(0);
//                    mealLiveData.postValue(detailedMeal); // Post value from remote source
//                } else {
//                    // If no response from remote, fetch local data
//                    fetchLocalMeal(id, mealLiveData);
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.e(TAG, "Error fetching meal details from remote: " + throwable.getMessage());
//                // Fetch from local data in case of an error
//                fetchLocalMeal(id, mealLiveData);
//            }
//        });
//
//        return mealLiveData;
//    }
//
//    private void fetchLocalMeal(String id, MutableLiveData<Meal> mealLiveData) {
//        // Get data from local data source
//        LiveData<Meal> localMeal = mealsLocalDataSource.getSingledMeals(id);
//        localMeal.observeForever(new Observer<Meal>() {
//            @Override
//            public void onChanged(Meal meal) {
//                if (meal != null) {
//                    mealLiveData.postValue(meal);
//                }
//            }
//        });
//
//
//    }
//
//}
