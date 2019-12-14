package com.example.vizielod.crossit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public static final int CATEGORY_ALL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnWarmup = findViewById(R.id.btnWarmup);
        btnWarmup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent warmupIntent = new Intent(MainActivity.this, WarmupActivity.class);
                startActivity(warmupIntent);
            }
        });

        ImageButton btnWorkoutByCategory = findViewById(R.id.btnWorkoutByCategory);
        btnWorkoutByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutByCategoryIntent = new Intent(MainActivity.this, WorkoutCategoryActivity.class);
                startActivity(workoutByCategoryIntent);
            }
        });

        ImageButton btnStretching = findViewById(R.id.btnStretching);
        btnStretching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stretchingIntent = new Intent(MainActivity.this, StretchingActivity.class);
                startActivity(stretchingIntent);
            }
        });

        ImageButton btnSMR = findViewById(R.id.btnSMR);
        btnSMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smrIntent = new Intent(MainActivity.this, SMRActivity.class);
                startActivity(smrIntent);
            }
        });

        ImageButton btnCardio = findViewById(R.id.btnCardio);
        btnCardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardioIntent = new Intent(MainActivity.this, CardioActivity.class);
                startActivity(cardioIntent);
            }
        });

        ImageButton btnExercises = findViewById(R.id.btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exercisesIntent = new Intent(MainActivity.this, ExercisesActivity.class);
                startActivity(exercisesIntent);
            }
        });

        ImageButton btnWorkouts = findViewById(R.id.btnWorkouts);
        btnWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutsIntent = new Intent(MainActivity.this, WorkoutsActivity.class);
                //workoutsIntent.putExtra(WorkoutsActivity.KEY_WORKOUT_CATEGORY, CATEGORY_ALL);
                startActivity(workoutsIntent);
            }
        });
    }
}
