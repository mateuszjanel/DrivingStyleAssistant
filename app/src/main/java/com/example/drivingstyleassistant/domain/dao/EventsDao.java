package com.example.drivingstyleassistant.domain.dao;

import com.example.drivingstyleassistant.domain.entities.Events;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventsDao {
    @Insert
    void insertAll(Events... events);

    @Insert
    long insert(Events event);

    @Query("UPDATE EVENTS SET g_force = :gforce, degree = :degree, grade_loss = :gradeloss, event_type = :eventtype WHERE id = :ID")
    void updateEvent(long ID, float gforce, int degree, float gradeloss, Events.EventType eventtype);

    @Query("SELECT * FROM events")
    List<Events> getEvents();

    @Query("SELECT * FROM events WHERE id = :id")
    Events getEventById(long id);

    @Query("SELECT * FROM events WHERE event_type = :eventtype")
    List<Events> getEventsByType(Events.EventType eventtype);
}
