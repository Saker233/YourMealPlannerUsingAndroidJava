package com.example.yourmealplanner.Network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("categories.php")
    Call<CategoryResponse> getCategory();
}
