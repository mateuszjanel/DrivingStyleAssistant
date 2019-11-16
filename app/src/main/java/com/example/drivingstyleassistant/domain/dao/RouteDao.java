package com.example.drivingstyleassistant.domain.dao;

import com.example.drivingstyleassistant.domain.entities.Route;

import java.sql.Date;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RouteDao {

    @Insert
    void insertAll(Route... routes);

    @Insert
    void insert(Route route);

    @Query("UPDATE routes SET route_date = :_routeDate, mark = :_mark, sudden_breakings = :_suddenBreakings, sudden_accelerations = :_suddenAccelerations, smoothness = :_smoothness, dangerous_cornering = :_dangerousCornering WHERE id = :_id")
    void updateRoute(int _id, Date _routeDate, float _mark, float _suddenBreakings, float _suddenAccelerations, float _smoothness, float _dangerousCornering);
}
