package com.example.drivingstyleassistant.domain.helpers;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;
import com.example.drivingstyleassistant.domain.entities.Events;

import java.sql.Date;
import java.util.List;

public class EventHelper {
    ContextService contextService = ContextService.getContextService();
    AppDatabase appDatabase = AppDatabase.getAppDatabase(contextService.appContext);

    public void addEventAcceleration(long routeId, float gradeLoss, float speed, int degree, float gForce, Events.EventType eventType, long timestamp){
        Date dateStamp = new Date(timestamp * 1000);
        Events events = new Events(dateStamp, gForce, degree, gradeLoss, eventType, routeId, speed);
        appDatabase.eventsDao().insert(events);
    }

    public boolean checkIfTransgression(float acceleration){
        boolean transgression = false;
        if((acceleration / 9.81) > 0.4 || (acceleration / 9.81) < -0.4){
            transgression = true;
        }
        return transgression;
    }

    public List<Events> getAllEventsOfType(Events.EventType type){
        return appDatabase.eventsDao().getEventsByType(type);
    }

    public Events.EventType getEventType(String type){
        Events.EventType eventType;
        if(type == "accelerating"){
            eventType = Events.EventType.AggressiveAcceleration;
        }
        else if(type == "braking"){
            eventType = Events.EventType.SuddenBraking;
        }
        else if (type == "cornering"){
            eventType = Events.EventType.DangerousCornering;
        }
        else eventType = null;

        return eventType;
    }
}
