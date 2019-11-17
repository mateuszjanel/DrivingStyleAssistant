package com.example.drivingstyleassistant.domain.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "events")
public class Events {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "g_force")
    private float gForce;
    @ColumnInfo(name = "degree")
    private int degree;
    @ColumnInfo(name = "grade_loss")
    private float gradeLoss;
    @ColumnInfo(name = "event_type")
    @TypeConverters(EventTypeConverter.class)
    EventType eventType;


    public enum EventType{
        AggressiveAcceleration(0),
        SuddenBraking(1),
        DangerousCornering(2),
        RapidDriving(3);

        private int code;

        EventType(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
    }
}
