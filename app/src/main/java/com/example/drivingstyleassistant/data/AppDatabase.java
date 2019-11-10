package com.example.drivingstyleassistant.data;

import com.example.drivingstyleassistant.data.repositories.RouteRepository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Route.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "DrivingStyleAssistantTestDB";

    public abstract RouteRepository routeRepository();
}
