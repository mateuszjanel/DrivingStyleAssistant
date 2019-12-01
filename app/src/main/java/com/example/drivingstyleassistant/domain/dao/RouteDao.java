package com.example.drivingstyleassistant.domain.dao;

import com.example.drivingstyleassistant.domain.entities.Route;

import java.sql.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RouteDao {

    @Insert
    void insertAll(Route... routes);

    @Insert
    long insert(Route route);

    @Query("UPDATE routes SET route_date = :_routeDate, mark = :_mark, sudden_breakings = :_breakingGrade, sudden_accelerations = :_acceleratingGrade, smoothness = :_smoothness, dangerous_cornering = :_dangerousCornering WHERE id = :_id")
    void updateRoute(long _id, Date _routeDate, float _mark, float _breakingGrade, float _acceleratingGrade, float _smoothness, float _dangerousCornering);

    @Query("UPDATE routes SET smoothness = :_smoothness WHERE id = :_id")
    void updateSmoothnessGrade(long _id, float _smoothness);

    @Query("SELECT * FROM routes")
    List<Route> getRoutes();

    @Query("SELECT * FROM routes WHERE id = :_id")
    Route getRouteById(long _id);

    @Query("SELECT * FROM routes WHERE route_date = :_date")
    List<Route> getRoutesByDate(Date _date);
}
