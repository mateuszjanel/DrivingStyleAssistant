package com.example.drivingstyleassistant.data;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Statistics {
    @PrimaryKey
    public int routeId;
    @ColumnInfo(name = "route_date")
    public Date routeDate;
    @ColumnInfo(name = "mark")
    public float mark;
}
