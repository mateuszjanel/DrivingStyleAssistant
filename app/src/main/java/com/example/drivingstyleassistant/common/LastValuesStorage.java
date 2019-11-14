package com.example.drivingstyleassistant.common;

import java.util.Date;

public class LastValuesStorage {
    static Date timestamp;
    static float[] lastSpeedValue = {-1, -1, -1, -1, -1};
    static float[] lastAccelerationValueX = {-1, -1, -1, -1, -1};
    static float[] lastAccelerationValueY = {-1, -1, -1, -1, -1};
    static float[] lastAccelerationValueZ = {-1, -1, -1, -1, -1};
    static float[] lastBearing = {-1, -1, -1, -1, -1};

    private static LastValuesStorage INSTANCE;

    /*private LastValuesStorage(){
        lastSpeedValue = new float[5];
        lastAccelerationValueX = new float[5];
        lastAccelerationValueY = new float[5];
        lastAccelerationValueZ = new float[5];
        lastBearing = new float[5];
    }*/

    public static LastValuesStorage getLastValuesStorage(){
        if(INSTANCE == null) {
            INSTANCE = new LastValuesStorage();
        }
        return INSTANCE;
    }
}
