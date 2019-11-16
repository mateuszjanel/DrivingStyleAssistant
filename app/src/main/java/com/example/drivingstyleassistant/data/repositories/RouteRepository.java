package com.example.drivingstyleassistant.data.repositories;

import android.content.Context;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;

public class RouteRepository{

    ContextService contextService = ContextService.getContextService();
    Context appContext = contextService.appContext;
    AppDatabase appDatabase = AppDatabase.getAppDatabase(appContext);



    public RouteRepository() {
    }
}
