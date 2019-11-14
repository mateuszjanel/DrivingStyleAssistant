package com.example.drivingstyleassistant.data.repositories;

import android.app.Application;

import com.example.drivingstyleassistant.domain.AppDatabase;

public class RouteRepository implements Application.ActivityLifecycleCallbacks{

//    Context appContext = getActivity().getApplicationContext();
    AppDatabase appDatabase = AppDatabase.getAppDatabase(appContext);

}
