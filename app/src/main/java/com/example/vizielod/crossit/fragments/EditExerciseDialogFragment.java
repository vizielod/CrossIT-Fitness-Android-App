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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vizielod.crossit.R;
import com.example.vizielod.crossit.data.Exercise;

public class EditExerciseDialogFragment extends DialogFragment {

    public static final String TAG = "EditExerciseDialogFragment";

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText idealRestTimeEditText;
    private EditText idealVolumeEditText;
    private Spinner categorySpinner;
    private CheckBox alreadyFinishedCheckBox;

    public interface EditExerciseDialogListener {
        void onExerciseCreated(Exercise exercise);
        //void onExerciseEdited(Exercise exercise);
    }

    private EditExerciseDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof EditExerciseDialogListener) {
            listener = (EditExerciseDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the EditExerciseDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.edit_exercise)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isValid()){
                            listener.onExerciseCreated(getExercise());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid(){
        return nameEditText.getText().length()>0;
    }

    private Exercise getExercise() {
        Exercise exercise = new Exercise();
        exercise.name = nameEditText.getText().toString();
        exercise.description = descriptionEditText.getText().toString();
        exercise.restTime = idealRestTimeEditText.getText().toString();
        exercise.volume = idealVolumeEditText.getText().toString();
        exercise.category = Exercise.Category.getByOrdinal(categorySpinner.getSelectedItemPosition());
        exercise.isFinished = alreadyFinishedCheckBox.isChecked();
        return exercise;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_exercise, null);
        nameEditText = contentView.findViewById(R.id.ExerciseNameEditText);
        descriptionEditText = contentView.findViewById(R.id.ExerciseDescriptionEditText);
        idealRestTimeEditText = contentView.findViewById(R.id.ExerciseIdealRestTimeEditText);
        idealVolumeEditText = contentView.findViewById(R.id.ExerciseIdealVolumeEditText);
        categorySpinner = contentView.findViewById(R.id.ExerciseCategorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_exercises)));
        alreadyFinishedCheckBox = contentView.findViewById(R.id.ExerciseIsFinishedCheckBox);
        return contentView;
    }

}
