package com.example.drivingstyleassistant.domain;

import android.content.Context;

import com.example.drivingstyleassistant.domain.dao.EventsDao;
import com.example.drivingstyleassistant.domain.dao.RouteDao;
import com.example.drivingstyleassistant.domain.entities.DateConverter;
import com.example.drivingstyleassistant.domain.entities.EventTypeConverter;
import com.example.drivingstyleassistant.domain.entities.Events;
import com.example.drivingstyleassistant.domain.entities.Route;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Route.class, Events.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, EventTypeConverter.class})


public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "DrivingStyleAssistantTestDB";
    private static AppDatabase INSTANCE;

    public abstract RouteDao routeDao();
    public abstract EventsDao eventsDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
