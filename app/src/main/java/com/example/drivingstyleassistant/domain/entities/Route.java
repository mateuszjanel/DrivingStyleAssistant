package com.example.drivingstyleassistant.domain.entities;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routes")
public class Route {
    @PrimaryKey (autoGenerate = true)
    private long id;
    @ColumnInfo(name = "route_date")
    private Date routeDate;
    @ColumnInfo(name = "mark")
    private float mark;
    @ColumnInfo(name = "sudden_breakings")
    private float breakingGrade;
    @ColumnInfo(name = "sudden_accelerations")
    private float acceleratingGrade;
    @ColumnInfo(name = "smoothness")
    private float smoothness;
    @ColumnInfo(name = "dangerous_cornering")
    private float dangerousCornering;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRouteDate() {
        return routeDate;
    }

    public void setRouteDate(Date routeDate) {
        this.routeDate = routeDate;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public float getBreakingGrade() {
        return breakingGrade;
    }

    public void setSuddenBreakingsNumber(float breakingGrade) {
        this.breakingGrade = breakingGrade;
    }

    public float getAcceleratingGrade() {
        return acceleratingGrade;
    }

    public void setAcceleratingGrade(float acceleratingGrade) {
        this.acceleratingGrade = acceleratingGrade;
    }

    public float getSmoothness() {
        return smoothness;
    }

    public void setSmoothness(float smoothness) {
        this.smoothness = smoothness;
    }

    public float getDangerousCornering() {
        return dangerousCornering;
    }

    public void setDangerousCornering(float dangerousCornering) {
        this.dangerousCornering = dangerousCornering;
    }

    public Route(Date routeDate) {
        this.routeDate = routeDate;
    }
}
