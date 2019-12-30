package com.example.drivingstyleassistant.domain.entities;

import java.sql.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "events",
        foreignKeys = @ForeignKey(entity = Events.class,
                parentColumns = "id",
                childColumns = "route_id",
                onDelete = ForeignKey.NO_ACTION))
public class Events {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "timestamp")
    private Date timestamp;
    @ColumnInfo(name = "g_force")
    private float gForce;
    @ColumnInfo(name = "degree")
    private int degree;
    @ColumnInfo(name = "grade_loss")
    private float gradeLoss;
    @ColumnInfo(name = "speed")
    private float speed;
    @ColumnInfo(name = "event_type")
    EventType eventType;
    @ColumnInfo(name = "route_id")
    private long routeId;


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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getGForce() {
        return gForce;
    }

    public void setGForce(float gForce) {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public float getSpeed() {
        return speed;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Events(Date timestamp, float gForce, int degree, float gradeLoss, EventType eventType, long routeId, float speed) {
        this.timestamp = timestamp;
        this.gForce = gForce;
        this.degree = degree;
        this.gradeLoss = gradeLoss;
        this.eventType = eventType;
        this.routeId = routeId;
        this.speed = speed;
    }
}
