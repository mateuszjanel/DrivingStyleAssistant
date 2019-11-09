package com.example.drivingstyleassistant.data;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Route {
    @PrimaryKey (autoGenerate = true)
    public int routeId;
    @ColumnInfo(name = "route_date")
    public Date routeDate;
    @ColumnInfo(name = "mark")
    public float mark;
    @ColumnInfo(name = "sudden_breakings")
    public float suddenBreakingsNumber;
    @ColumnInfo(name = "sudden_accelerations")
    public float suddenAccelerationsNumber;
    @ColumnInfo(name = "smoothness")
    public float smoothness;
    @ColumnInfo(name = "dangerous_cornering")
    public float dangerousCornering;
}
