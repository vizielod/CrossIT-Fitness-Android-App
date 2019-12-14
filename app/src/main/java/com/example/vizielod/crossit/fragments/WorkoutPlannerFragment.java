package com.example.vizielod.crossit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vizielod.crossit.R;
import com.example.vizielod.crossit.adapter.WorkoutAdapter;
import com.example.vizielod.crossit.data.Workout;
import com.example.vizielod.crossit.data.WorkoutListDatabase;

import java.util.List;

public class WorkoutPlannerFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;

    private WorkoutListDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workout, container, false);


        TextView workoutNameTextView = rootView.findViewById(R.id.DetailedWorkoutViewName);
        TextView workoutCategoryTextView = rootView.findViewById(R.id.DetailedWorkoutViewCategory);
        TextView workoutDescriptionTextView = rootView.findViewById(R.id.DetailedWorkoutViewDescription);

        List<Workout> workouts = database.workoutDAO().getAll();

        /*workoutNameTextView.setText(person.getName());
        workoutCategoryTextView.setText(person.getEmail());
        workoutDescriptionTextView.setText(person.getAddress());*/

        return rootView;
    }
}
