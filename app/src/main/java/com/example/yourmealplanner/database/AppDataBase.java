package com.example.yourmealplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.yourmealplanner.Home.model.Meal;

@Database(entities = {Meal.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;


    public abstract MealDao getMealDao();


    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDataBase.class, "mealsdb")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Meal ADD COLUMN weekday TEXT DEFAULT NULL");
        }
    };
}
