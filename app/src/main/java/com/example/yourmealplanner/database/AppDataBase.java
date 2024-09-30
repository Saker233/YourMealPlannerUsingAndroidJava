package com.example.yourmealplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.yourmealplanner.Home.model.Meal;

@Database(entities = {Meal.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    // Abstract method to get the DAO
    public abstract MealDao getMealDao();

    // Singleton pattern to get a single instance of the database
    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDataBase.class, "mealsdb")
                    .addMigrations(MIGRATION_2_3)  // Add migration for database version upgrade
                    .build();
        }
        return instance;
    }

    // Define migration from version 1 to 2
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Meal ADD COLUMN assignedDate TEXT DEFAULT NULL");
        }
    };
}
