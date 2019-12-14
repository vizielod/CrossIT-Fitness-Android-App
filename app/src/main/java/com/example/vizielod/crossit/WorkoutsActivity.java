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

import com.example.vizielod.crossit.adapter.WorkoutAdapter;
import com.example.vizielod.crossit.data.Workout;
import com.example.vizielod.crossit.data.WorkoutListDatabase;
import com.example.vizielod.crossit.fragments.NewWorkoutDialogFragment;

import java.util.List;

public class WorkoutsActivity extends AppCompatActivity
        implements WorkoutAdapter.WorkoutClickListener,
        NewWorkoutDialogFragment.NewWorkoutDialogListener {


    public static final String KEY_WORKOUT_CATEGORY = "KEY_WORKOUT_CATEGORY";

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;

    private WorkoutListDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final Workout.Category workoutCategory = Workout.Category.getByOrdinal(intent.getIntExtra(KEY_WORKOUT_CATEGORY, -1));
        //final Workout.Category workoutCategory = intent.getIntExtra(KEY_WORKOUT_CATEGORY, -1);
        //String category = getCategoryString(workoutCategory);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewWorkoutDialogFragment().show(getSupportFragmentManager(), NewWorkoutDialogFragment.TAG);
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                WorkoutListDatabase.class,
                "workouts"
        ).build();

        initRecyclerView();
        if (workoutCategory == null) {
            loadItemsInBackground();
        } else {
            loadItemsInBackgroundByCategory(workoutCategory);
        }

    }

    private String getCategoryString(int workoutCategory) {
        switch (workoutCategory) {
            case WorkoutCategoryActivity.CATEGORY_WEIGHT_TRAINING:
                return "WEIGHT_TRAINING";
            case WorkoutCategoryActivity.CATEGORY_CROSSFIT:
                return "CROSSFIT";
            case WorkoutCategoryActivity.CATEGORY_FUNCTIONAL_CROSS_TRAINING:
                return "FUNCTIONAL_CROSS_TRAINING";
            case WorkoutCategoryActivity.CATEGORY_POWER_LIFTING:
                return "POWER_LIFTING";
            case WorkoutCategoryActivity.CATEGORY_WEIGHT_LIFTING:
                return "WEIGHT_LIFTING";
            case WorkoutCategoryActivity.CATEGORY_BODYWEIGHT_WORKOUTS:
                return "BODYWEIGHT_WORKOUTS";
            default:
                return null;
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.WorkoutsRecyclerView);
        adapter = new WorkoutAdapter(this, this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerViewByCategory(Workout.Category category) {
        recyclerView = findViewById(R.id.WorkoutsRecyclerView);
        adapter = new WorkoutAdapter(this, this);
        loadItemsInBackgroundByCategory(category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //Háttérszálon betölti az adatbázisba mentett elemeket, majd UI szálon átadja őket az adapternek
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Workout>>() {

            //háttérszálon
            @Override
            protected List<Workout> doInBackground(Void... voids) {
                return database.workoutDAO().getAll();
            }

            //UI szálon
            @Override
            protected void onPostExecute(List<Workout> workouts) {
                adapter.update(workouts);
            }
        }.execute();
    }

    private void loadItemsInBackgroundByCategory(final Workout.Category category) {
        new AsyncTask<Workout.Category, Void, List<Workout>>() {
            //Workout.Category cat = category;
            //háttérszálon
            @Override
            protected List<Workout> doInBackground(Workout.Category... categories) {
                return database.workoutDAO().getByCategory(categories[0]);
            }

            //UI szálon
            @Override
            protected void onPostExecute(List<Workout> workouts) {
                //workouts = database.workoutDAO().getByCategory(category);
                adapter.update(workouts);
            }
        }.execute(category);
    }

    @Override
    public void onWorkoutChanged(final Workout workout) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.workoutDAO().update(workout);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("ExerciseActivity", "Exercise update was successful");
            }
        }.execute();
    }

    @Override
    public void onWorkoutCreated(final Workout workout) {
        new AsyncTask<Void, Void, Workout>() {

            @Override
            protected Workout doInBackground(Void... voids) {
                workout.id = database.workoutDAO().insert(workout);
                return workout;
            }

            @Override
            protected void onPostExecute(Workout workout) {
                adapter.addWorkout(workout);
            }
        }.execute();
    }

    public void onWorkoutDeleted(final Workout workout){
        new AsyncTask<Void, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                database.workoutDAO().deleteWorkout(workout);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("WorkoutsActivity", "Workout delete was successful");
            }
        }.execute();
    }

}
