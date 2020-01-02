package com.example.drivingstyleassistant.data;

import android.hardware.SensorEvent;

import com.example.drivingstyleassistant.domain.entities.Events;
import com.example.drivingstyleassistant.domain.helpers.EventHelper;
import com.example.drivingstyleassistant.domain.helpers.RouteHelper;

public class CorneringGrade implements GradeHelper {

    float accelerationInG;
    float gradeLoss;
    Events.EventType eventType;
    int degree;


    float analyzeData(SensorEvent sensorEvent, long routeId) {

        float acceleration = sensorEvent.values[2];
        float accelerationInG = (float)(acceleration/9.81);
        return accelerationInG;
    }

    public float grade(SensorEvent sensorEvent, long routeId, float speed, float accelerationInG){
//        accelerationInG = analyzeData(sensorEvent, routeId);
        float previousGrade;
        this.accelerationInG = accelerationInG;
        RouteHelper routeHelper = new RouteHelper();
        EventHelper eventHelper = new EventHelper();
        analyze();
        previousGrade = routeHelper.getGradeFromRoute("cornering", routeId);
        eventType = Events.EventType.valueOf("DangerousCornering");
        speed = (float) (Math.round(speed * 10.0)/10.0);
        eventHelper.addEventAcceleration(routeId, gradeLoss, speed, degree, accelerationInG, eventType, sensorEvent.timestamp);

        routeHelper.updateGrade(gradeLoss, routeId, "cornering");

        return gradeLoss;
    }

    public void gradeOnly(float gradeAdd, long routeId){
        RouteHelper routeHelper = new RouteHelper();
        routeHelper.updateGrade(gradeAdd * (-1), routeId, "cornering");
    }


    @Override
    public void analyze() {
        float accelerationInGAbs = Math.abs(accelerationInG);
        if(accelerationInGAbs <= 0.5){
            gradeLoss = 0.25f;
            degree = 1;
        }
        else if(accelerationInGAbs > 0.5 && accelerationInG <= 0.75){
            gradeLoss = 0.5f;
            degree = 2;
        }
        else if(accelerationInGAbs > 0.75 && accelerationInG <= 1){
            gradeLoss = 0.8f;
            degree = 3;
        }
        else if(accelerationInGAbs > 1){
            gradeLoss = 1f;
            degree = 4;
        }
    }
    @Override
    public float grade(){
        return 0;
    }
}
