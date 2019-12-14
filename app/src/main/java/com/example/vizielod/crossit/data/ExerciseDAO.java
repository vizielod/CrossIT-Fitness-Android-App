package com.example.vizielod.crossit.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Query("SELECT * FROM Exercise")
    List<Exercise> getAll();

    @Insert
    long insert(Exercise exercises);

    @Update
    void update(Exercise exercise);

    @Delete
    void deleteExercise(Exercise exercise);
}
