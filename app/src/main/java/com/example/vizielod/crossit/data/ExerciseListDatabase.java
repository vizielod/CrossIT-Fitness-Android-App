package com.example.vizielod.crossit.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(
        entities = {Exercise.class},
        version = 1
)
@TypeConverters(value = {Exercise.Category.class})
public abstract class ExerciseListDatabase extends RoomDatabase {
    public abstract ExerciseDAO exerciseDAO();
}
