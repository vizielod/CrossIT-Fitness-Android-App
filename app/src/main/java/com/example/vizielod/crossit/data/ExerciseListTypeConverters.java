package com.example.vizielod.crossit.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ExerciseListTypeConverters {
    private static Gson gson = new Gson();



    @TypeConverter
    public static List<Exercise> stringToExerciseList(String data) {

        if (data == null) {

            return Collections.emptyList();

        }



        Type listType = new TypeToken<List<Exercise>>() {}.getType();



        return gson.fromJson(data, listType);

    }



    @TypeConverter

    public static String ExerciseListToString(List<Exercise> exercises) {

        return gson.toJson(exercises);

    }
}
