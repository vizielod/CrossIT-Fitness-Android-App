package com.example.vizielod.crossit;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vizielod.crossit.adapter.ExerciseAdapter;
import com.example.vizielod.crossit.adapter.WorkoutAdapter;
import com.example.vizielod.crossit.data.Exercise;
import com.example.vizielod.crossit.data.ExerciseListDatabase;
import com.example.vizielod.crossit.data.Workout;
import com.example.vizielod.crossit.data.WorkoutListDatabase;
import com.example.vizielod.crossit.fragments.NewExerciseDialogFragment;

import java.util.List;

public class DetailedWorkoutActivity extends AppCompatActivity
        implements ExerciseAdapter.ExerciseClickListener,
        NewExerciseDialogFragment.NewExerciseDialogListener{

    public static final String KEY_WORKOUT_ID = "KEY_WORKOUT_ID";

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private WorkoutAdapter workoutAdapter;

    private WorkoutListDatabase workoutDatabase;
    private ExerciseListDatabase exerciseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        final long workoutID = intent.getLongExtra(KEY_WORKOUT_ID, -1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewExerciseDialogFragment().show(getSupportFragmentManager(), NewExerciseDialogFragment.TAG_NEW);
            }
        });

        workoutDatabase = Room.databaseBuilder(
                getApplicationContext(),
                WorkoutListDatabase.class,
                "workouts"
        ).build();

        exerciseDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ExerciseListDatabase.class,
                "exercises"
        ).build();

        //initRecyclerView();
        loadWorkout(workoutID);


        /*if(workoutID == 0){
            loadItemsInBackground();
        }
        else{
            loadExercisesByWorkoudId(workoutID);
        }*/

        //Workout workout = database.workoutDAO().getByID(workoutID);

    }

    private void loadWorkout(final long id) {
        new AsyncTask<Long, Void, Workout>() {
            @Override
            protected Workout doInBackground (Long... ids) {
                return workoutDatabase.workoutDAO().getByID(ids[0]);
            }

            @Override
            protected void onPostExecute (Workout workout) {
                TextView workoutNameTextView = findViewById(R.id.DetailedWorkoutViewName);
                TextView workoutCategoryTextView = findViewById(R.id.DetailedWorkoutViewCategory);
                TextView workoutDescriptionTextView = findViewById(R.id.DetailedWorkoutViewDescription);

                workoutNameTextView.setText(workout.getName());
                workoutDescriptionTextView.setText(workout.getDescription());
                workoutCategoryTextView.setText(workout.getCategory().toString());
                //loadExercisesByWorkoudId();
                initRecyclerView();
            }
        }.execute(id);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.ExercisesRecyclerView);
        exerciseAdapter = new ExerciseAdapter(this);
        loadItemsInBackground();
        //loadExercisesByWorkoudId(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exerciseAdapter);
    }


    //Háttérszálon betölti az adatbázisba mentett elemeket, majd UI szálon átadja őket az adapternek
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Exercise>>() {

            //háttérszálon
            @Override
            protected List<Exercise> doInBackground(Void... voids) {
                return exerciseDatabase.exerciseDAO().getAll();
            }

            //UI szálon
            @Override
            protected void onPostExecute(List<Exercise> exercises) {
                exerciseAdapter.update(exercises);
            }
        }.execute();
    }

    private void loadExercisesByWorkoudId() {
        new AsyncTask<Void, Void, List<Exercise>>() {

            //háttérszálon
            @Override
            protected List<Exercise> doInBackground(Void... voids) {
                return exerciseDatabase.exerciseDAO().getAll();
            }

            //UI szálon
            @Override
            protected void onPostExecute(List<Exercise> exercises) {
                exerciseAdapter.update(exercises);
            }
        }.execute();
    }

    @Override
    public void onExerciseChanged(final Exercise exercise) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                exerciseDatabase.exerciseDAO().update(exercise);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("ExerciseActivity", "Exercise update was successful");
            }
        }.execute();
    }

    //NewExerciseDialogListener implementalasa
    @Override
    public void onExerciseCreated(final Exercise exercise) {
        new AsyncTask<Void, Void, Exercise>() {

            @Override
            protected Exercise doInBackground(Void... voids) {
                exercise.id = exerciseDatabase.exerciseDAO().insert(exercise);
                return exercise;
            }

            @Override
            protected void onPostExecute(Exercise exercise1) {
                exerciseAdapter.addExercise(exercise);
            }
        }.execute();
    }

    public void onExerciseDeleted(final Exercise exercise){
        new AsyncTask<Void, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                exerciseDatabase.exerciseDAO().deleteExercise(exercise);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("ExerciseActivity", "Exercise delete was successful");
            }
        }.execute();
    }

}
