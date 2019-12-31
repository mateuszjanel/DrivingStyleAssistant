package com.example.drivingstyleassistant.domain.helpers;

public class CommonHelper {
    float checkGradeLimits (float newGrade){
        if(newGrade > 5){
            return 5;
        }
        else if(newGrade < 1){
            return 1;
        }
        else {
            return newGrade;
        }
    }
}
