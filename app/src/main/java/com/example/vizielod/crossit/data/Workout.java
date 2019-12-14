package com.example.vizielod.crossit.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import java.util.List;

@Entity(tableName = "workouts")
public class Workout {

    public enum Category{
        WEIGHT_TRAINING, CROSSFIT, FUNCTIONAL_CROSS_TRAINING, POWER_LIFTING, WEIGHT_LIFTING, BODYWEIGHT_WORKOUTS;

        @TypeConverter
        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for (Category cat : Category.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category category) {
            return category.ordinal();
        }

        @Override
        public String toString(){
            String str;
            switch (this) {
                case WEIGHT_TRAINING:
                    str = "Weight Training";
                    break;
                case CROSSFIT:
                    str = "CrossFit";
                    break;
                case FUNCTIONAL_CROSS_TRAINING:
                    str = "Functional Cross Training";
                    break;
                case POWER_LIFTING:
                    str = "Power Lifting";
                    break;
                case WEIGHT_LIFTING:
                    str = "Weight Lifting";
                    break;
                case BODYWEIGHT_WORKOUTS:
                    str = "Bodyweight Workouts";
                    break;
                default:
                    str = null;
            }return str;
        }
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public Long getID(){
        return id;
    }

    @ColumnInfo(name = "name")
    public String name;

    public String getName(){
        return name;
    }

    @ColumnInfo(name = "description")
    public String description;

    public String getDescription(){
        return description;
    }
    @ColumnInfo(name = "category")
    public Category category;
    public Category getCategory(){
        return category;
    }

    /*@ColumnInfo(name = "exercises")
    public List<Exercise> exercises = null;*/

    //Exercise ID-kat tárolni, összefűzni azokat string-é majd abból visszaolvasni a megjelenítéshez.

    @Ignore
    public List<Exercise> exercises;

    public List<Exercise> getExercises(){
        return exercises;
    }

    public void setExercises(List<Exercise> exercises){
        this.exercises.clear();
        this.exercises.addAll(exercises);
    }



}

