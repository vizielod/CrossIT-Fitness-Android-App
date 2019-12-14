package com.example.vizielod.crossit;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.vizielod.crossit.adapter.ExerciseAdapter;
import com.example.vizielod.crossit.data.Exercise;
import com.example.vizielod.crossit.data.ExerciseListDatabase;
import com.example.vizielod.crossit.fragments.NewExerciseDialogFragment;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity
    implements ExerciseAdapter.ExerciseClickListener,
    NewExerciseDialogFragment.NewExerciseDialogListener{


    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;

    private ExerciseListDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewExerciseDialogFragment().show(getSupportFragmentManager(), NewExerciseDialogFragment.TAG_NEW);
                //new NewExerciseDialogFragment();
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                ExerciseListDatabase.class,
                "exercises"
        ).build();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.ExercisesRecyclerView);
        adapter = new ExerciseAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //Háttérszálon betölti az adatbázisba mentett elemeket, majd UI szálon átadja őket az adapternek
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Exercise>>() {

            //háttérszálon
            @Override
            protected List<Exercise> doInBackground(Void... voids) {
                return database.exerciseDAO().getAll();
            }

            //UI szálon
            @Override
            protected void onPostExecute(List<Exercise> exercises) {
                adapter.update(exercises);
            }
        }.execute();
    }

    @Override
    public void onExerciseChanged(final Exercise exercise) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.exerciseDAO().update(exercise);
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
                exercise.id = database.exerciseDAO().insert(exercise);
                return exercise;
            }

            @Override
            protected void onPostExecute(Exercise exercise1) {
                adapter.addExercise(exercise);
            }
        }.execute();
    }

    public void onExerciseDeleted(final Exercise exercise){
        new AsyncTask<Void, Void, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                database.exerciseDAO().deleteExercise(exercise);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("ExerciseActivity", "Exercise delete was successful");
            }
        }.execute();
    }

}
