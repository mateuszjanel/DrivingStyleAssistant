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

    @Query("UPDATE routes SET route_date = :routeDate, mark = :mark, sudden_breakings = :breakingGrade, sudden_accelerations = :acceleratingGrade, smoothness = :smoothness, dangerous_cornering = :dangerousCornering WHERE id = :id")
    void updateRoute(long id, Date routeDate, float mark, float breakingGrade, float acceleratingGrade, float smoothness, float dangerousCornering);

    @Query("UPDATE routes SET smoothness = :smoothness WHERE id = :id")
    void updateSmoothnessGrade(long id, float smoothness);

    @Query("SELECT * FROM routes")
    List<Route> getRoutes();

    @Query("SELECT * FROM routes WHERE id = :id")
    Route getRouteById(long id);

    @Query("SELECT * FROM routes WHERE route_date = :date")
    List<Route> getRoutesByDate(Date date);
}
