package com.example.drivingstyleassistant.domain.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "events",
        foreignKeys = @ForeignKey(entity = Events.class,
                parentColumns = "id",
                childColumns = "route_id",
                onDelete = ForeignKey.NO_ACTION))
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
    @ColumnInfo(name = "route_id")
    private int routeId;


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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getgForce() {
        return gForce;
    }

    public void setgForce(float gForce) {
        this.gForce = gForce;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public float getGradeLoss() {
        return gradeLoss;
    }

    public void setGradeLoss(float gradeLoss) {
        this.gradeLoss = gradeLoss;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Events(int id, float gForce, int degree, float gradeLoss, EventType eventType, int routeId) {
        this.id = id;
        this.gForce = gForce;
        this.degree = degree;
        this.gradeLoss = gradeLoss;
        this.eventType = eventType;
        this.routeId = routeId;
    }
}
