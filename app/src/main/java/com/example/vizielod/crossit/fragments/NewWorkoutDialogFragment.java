package com.example.vizielod.crossit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vizielod.crossit.R;
import com.example.vizielod.crossit.data.Workout;

public class NewWorkoutDialogFragment extends DialogFragment {

    public static final String TAG = "NewWorkoutDialogFragment";

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;

    public interface NewWorkoutDialogListener {
        void onWorkoutCreated(Workout newWorkout);
    }

    private NewWorkoutDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewWorkoutDialogListener) {
            listener = (NewWorkoutDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_workout)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isValid()){
                            listener.onWorkoutCreated(getWorkout());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private Workout getWorkout() {
        Workout workout = new Workout();
        workout.name = nameEditText.getText().toString();
        workout.description = descriptionEditText.getText().toString();
        workout.category = Workout.Category.getByOrdinal(categorySpinner.getSelectedItemPosition());
        return workout;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_workout, null);
        nameEditText = contentView.findViewById(R.id.WorkoutNameEditText);
        descriptionEditText = contentView.findViewById(R.id.WorkoutDescriptionEditText);
        categorySpinner = contentView.findViewById(R.id.WorkoutCategorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_workouts)));
        return contentView;
    }
}
