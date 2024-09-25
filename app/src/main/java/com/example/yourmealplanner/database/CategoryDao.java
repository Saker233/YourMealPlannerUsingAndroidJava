package com.example.yourmealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yourmealplanner.Home.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategories(List<Category> categories);

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM Category WHERE idCategory = :id LIMIT 1")
    LiveData<Category> getCategoryById(String id);

    @Delete
    void deleteCategory(Category category);
}
