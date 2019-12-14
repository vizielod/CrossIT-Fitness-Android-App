package com.example.vizielod.crossit.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WorkoutDAO {
    @Query("SELECT * FROM workouts")
    List<Workout> getAll();


    @Query("SELECT * FROM workouts WHERE Category=:category")
    List<Workout> getByCategory(Workout.Category category);

    @Query("SELECT * FROM workouts WHERE id=:id LIMIT 1")
    Workout getByID(Long id);

    @Insert
    long insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);
}
