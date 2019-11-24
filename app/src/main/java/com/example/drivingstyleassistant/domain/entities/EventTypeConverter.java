package com.example.drivingstyleassistant.domain.entities;

import androidx.room.TypeConverter;

import static com.example.drivingstyleassistant.domain.entities.Events.EventType.*;

public class EventTypeConverter {

    @TypeConverter
    public static Events.EventType toEventType(int type){
        if(type == AggressiveAcceleration.getCode()){
            return AggressiveAcceleration;
        }
            else if (type == SuddenBraking.getCode()) {
            return SuddenBraking;
        }
        else if (type == DangerousCornering.getCode()){
            return DangerousCornering;
        }
        else if (type == RapidDriving.getCode()){
            return RapidDriving;
        }
        else{
            throw new IllegalArgumentException("Could not recognize event type");
        }
    }

    public static int toInt(Events.EventType type){
        return type.getCode();
    }
}
