package com.example.vizielod.crossit.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(
        entities = {Workout.class},
        version = 1
)
@TypeConverters(value = {Workout.Category.class})
public abstract class WorkoutListDatabase extends RoomDatabase {
    public abstract WorkoutDAO workoutDAO();
}
