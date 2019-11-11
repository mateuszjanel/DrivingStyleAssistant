package com.example.drivingstyleassistant.data;

import android.content.Context;

import com.example.drivingstyleassistant.data.dao.RouteDao;
import com.example.drivingstyleassistant.data.entities.Route;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Route.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "DrivingStyleAssistantTestDB";
    private static AppDatabase INSTANCE;

    public abstract RouteDao routeDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    //.allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
