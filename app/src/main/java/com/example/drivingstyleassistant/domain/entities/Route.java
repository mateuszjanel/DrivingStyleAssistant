package com.example.drivingstyleassistant.domain.entities;

import java.sql.Date;

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
    @ColumnInfo(name = "braking_grade")
    private float breakingGrade;
    @ColumnInfo(name = "accelerating_grade")
    private float acceleratingGrade;
    @ColumnInfo(name = "smoothness")
    private float smoothness;
    @ColumnInfo(name = "cornering_grade")
    private float dangerousCornering;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setBreakingGrade(float breakingGrade) {
        this.breakingGrade = breakingGrade;
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
        this.setAcceleratingGrade(5.0f);
        this.setDangerousCornering(5.0f);
        this.setSuddenBreakingsNumber(5.0f);
    }
}
