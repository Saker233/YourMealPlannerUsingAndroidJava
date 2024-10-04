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

    public abstract MealDao getMealDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDataBase.class, "mealsdb")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Meal ADD COLUMN assignedDate TEXT DEFAULT NULL");
        }
    };
}
