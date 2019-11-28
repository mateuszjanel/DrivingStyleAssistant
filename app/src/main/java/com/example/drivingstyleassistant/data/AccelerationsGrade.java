package com.example.drivingstyleassistant.data;

import android.hardware.SensorEvent;

import com.example.drivingstyleassistant.common.ContextService;
import com.example.drivingstyleassistant.domain.AppDatabase;

public class AccelerationsGrade implements GradeHelper{



    public void analyze(SensorEvent sensorEvent) {
        float previousGrade;
        previousGrade = appDatabase.
    }

    public float grade(SensorEvent sensorEvent){
        return 0;
    }


    @Override
    public void analyze() {
    }
    @Override
    public float grade(){
        return 0;
    }
}
