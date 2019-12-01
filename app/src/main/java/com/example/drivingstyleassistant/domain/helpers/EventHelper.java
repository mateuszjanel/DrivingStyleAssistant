package com.example.drivingstyleassistant.domain.helpers;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;
import com.example.drivingstyleassistant.domain.entities.Events;

import java.sql.Date;

public class EventHelper {
    ContextService contextService = ContextService.getContextService();
    AppDatabase appDatabase = AppDatabase.getAppDatabase(contextService.appContext);

    public void addEventAcceleration(long routeId, float gradeLoss, float speed, int degree, float gForce, Events.EventType eventType, long timestamp){
        Date dateStamp = new Date(timestamp * 1000);
        Events events = new Events(dateStamp, gForce, degree, gradeLoss, eventType, routeId, speed);
        appDatabase.eventsDao().insert(events);
    }
}
