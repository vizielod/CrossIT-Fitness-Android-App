package com.example.vizielod.crossit.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

@Entity(tableName = "exercise")
public class Exercise {
    public enum Category{
        WEIGHT_LIFTING, CROSSFIT, POWER_LIFTING, STRETCHING, SMR, CARDIO, WARMUP; //SMR = Self-myofascial Release

        @TypeConverter
        public static Category getByOrdinal(int ordinal){
            Category ret = null;
            for(Category cat : Category.values()){
                if(cat.ordinal() == ordinal){
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category cat){
            return cat.ordinal();
        }

        @Override
        public String toString(){
            String str;
            switch (this) {
                case WEIGHT_LIFTING:
                    str = "Weight Lifting";
                    break;
                case CROSSFIT:
                    str = "CrossFit";
                    break;
                case POWER_LIFTING:
                    str = "Power Lifting";
                    break;
                case STRETCHING:
                    str = "Stretching";
                    break;
                case SMR:
                    str = "SMR";
                    break;
                case CARDIO:
                    str = "Cardio";
                    break;
                case WARMUP:
                    str = "Warmup";
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

    @ColumnInfo(name = "volume")
    public String volume;

    public String getVolume(){
        return volume;
    }

    @ColumnInfo(name = "rest")
    public String restTime;

    public String getRestTime(){
        return restTime;
    }

    @ColumnInfo(name = "category")
    public Category category;

    public Category getCategory(){
        return category;
    }

    @ColumnInfo(name = "is_finished")
    public boolean isFinished;

    public boolean getIsFinished(){
        return isFinished;
    }

    //Valszeg euipment is kulon osztaly kene legyen ez meg tartalmazzon arrol egy listat
    /*public enum Equipment{
        FOAMROLL, KETLEBELL, BARBELL, DUMBELL, BODYWEIGHT, RESISTANCE_BAND, WORKOUT_MACHINE, ROWING_MACHINE, STEP_BOX;

        @TypeConverter
        public static Equipment getByOrdinal(int ordinal){
            Equipment ret = null;
            for(Equipment equip : Equipment.values()){
                if(equip.ordinal() == ordinal){
                    ret = equip;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Equipment equip){
            return equip.ordinal();
        }
    }*/
}
