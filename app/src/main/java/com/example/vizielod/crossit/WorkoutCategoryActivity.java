package com.example.vizielod.crossit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vizielod.crossit.data.Workout;

public class WorkoutCategoryActivity extends AppCompatActivity {

    public static final int CATEGORY_WEIGHT_TRAINING = 1;
    public static final int CATEGORY_CROSSFIT = 2;
    public static final int CATEGORY_FUNCTIONAL_CROSS_TRAINING= 3;
    public static final int CATEGORY_POWER_LIFTING = 4;
    public static final int CATEGORY_WEIGHT_LIFTING = 5;
    public static final int CATEGORY_BODYWEIGHT_WORKOUTS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutcategory);

        initButton(R.id.btnWeightTraining, Workout.Category.WEIGHT_TRAINING);
        initButton(R.id.btnCrossFit, Workout.Category.CROSSFIT);
        initButton(R.id.btnFunctionalCrossTraining, Workout.Category.FUNCTIONAL_CROSS_TRAINING);
        initButton(R.id.btnPowerLifting, Workout.Category.POWER_LIFTING);
        initButton(R.id.btnWeightLifting, Workout.Category.WEIGHT_LIFTING);
        initButton(R.id.btnBodyweightWorkouts, Workout.Category.BODYWEIGHT_WORKOUTS);
    }

    private void initButton (@IdRes int id, final Workout.Category category) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                i.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, category.ordinal()); //kulcs érték párok
                startActivity(i);
            }
        });
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutcategory);


        ImageButton btnWeightTraining = findViewById(R.id.btnWeightTraining);
        btnWeightTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weightTrainingIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                weightTrainingIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_WEIGHT_TRAINING); //kulcs érték párok
                startActivity(weightTrainingIntent);
            }
        });

        ImageButton btnCrossFit = findViewById(R.id.btnCrossFit);
        btnCrossFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent crossfitIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                crossfitIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_CROSSFIT); //kulcs érték párok
                startActivity(crossfitIntent);
            }
        });

        ImageButton btnFunctionalCrossTraining = findViewById(R.id.btnFunctionalCrossTraining);
        btnFunctionalCrossTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent functionalCrossTrainingIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                functionalCrossTrainingIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_FUNCTIONAL_CROSS_TRAINING); //kulcs érték párok
                startActivity(functionalCrossTrainingIntent);
            }
        });

        ImageButton btnPowerLifting = findViewById(R.id.btnPowerLifting);
        btnPowerLifting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent powerLiftinggIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                powerLiftinggIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_POWER_LIFTING); //kulcs érték párok
                startActivity(powerLiftinggIntent);
            }
        });

        ImageButton btnWeightLifting = findViewById(R.id.btnWeightLifting);
        btnWeightLifting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weightLiftingIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                weightLiftingIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_WEIGHT_LIFTING); //kulcs érték párok
                startActivity(weightLiftingIntent);
            }
        });

        ImageButton btnBodyweightWorkouts = findViewById(R.id.btnBodyweightWorkouts);
        btnBodyweightWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bodyweightWorkoutsIntent = new Intent(WorkoutCategoryActivity.this, WorkoutsActivity.class);
                bodyweightWorkoutsIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_BODYWEIGHT_WORKOUTS); //kulcs érték párok
                startActivity(bodyweightWorkoutsIntent);
            }
        });
    }*/
}
