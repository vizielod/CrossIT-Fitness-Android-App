package com.example.vizielod.crossit.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vizielod.crossit.R;
import com.example.vizielod.crossit.data.Exercise;

import java.util.ArrayList;
import java.util.List;


public class ExerciseAdapter
        extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private final List<Exercise> exercises;

    private ExerciseClickListener listener;

    public ExerciseAdapter(ExerciseClickListener listener) {
        this.listener = listener;
        exercises = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exercise_list, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public interface ExerciseClickListener{
        void onExerciseChanged(Exercise exercise);
        void onExerciseDeleted(Exercise exercise);
        void onExerciseCreated(Exercise exercise);
    }


    //Ezeken a mezőkön keresztül tudjuk majd elérni az egyes lista elemekhez tartozó nézeteket
    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        
        ImageView iconImageView;
        TextView nameTextView;
        TextView volumeTextView;
        TextView descriptionTextView;
        TextView categoryTextView;
        CheckBox isFinishedCheckBox;
        ImageButton editButton;
        ImageButton removeButton;

        Exercise exercise = new Exercise();

        ExerciseViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ExerciseIconImageView);
            nameTextView = itemView.findViewById(R.id.ExerciseNameTextView);
            descriptionTextView = itemView.findViewById(R.id.ExerciseDescriptionTextView);
            categoryTextView = itemView.findViewById(R.id.ExerciseCategoryTextView);
            volumeTextView = itemView.findViewById(R.id.ExerciseVolumeTextView);
            isFinishedCheckBox = itemView.findViewById(R.id.ExerciseIsFinishedCheckBox);
            editButton = itemView.findViewById(R.id.ExerciseEditButton);
            removeButton = itemView.findViewById(R.id.ExerciseRemoveButton);

            /*editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //exercisesActivity.setContentView(R.layout.activity_exercises);
                    new NewExerciseDialogFragment(view, exercise);/*.show(exercisesActivity.getSupportFragmentManager(), NewExerciseDialogFragment.TAG);
                }
            });*/

            removeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(exercise != null) {
                        deleteExercise(exercise);
                        listener.onExerciseDeleted(exercise);
                        Snackbar snackbarDelete = Snackbar.make(v, R.string.exercise_deleted_message, Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        Snackbar snackbarUndo = Snackbar.make(view, R.string.exercise_restored_message, Snackbar.LENGTH_LONG);
                                        snackbarUndo.show();
                                        listener.onExerciseCreated(exercise);
                                    }
                                });
                        snackbarDelete.show();
                    }
                }
            });

            isFinishedCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if(exercise != null){
                        exercise.isFinished = isChecked;
                        listener.onExerciseChanged(exercise);
                    }
                }
            });
        }

        /*@Override
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
        }*/

    }

    //Kössük hozzá a megfelelő modell elem tulajdonságait a lista elem nézeteihez.
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.nameTextView.setText(exercise.name);
        holder.descriptionTextView.setText(exercise.description);
        holder.categoryTextView.setText(exercise.category.toString());
        holder.volumeTextView.setText(exercise.volume);
        holder.iconImageView.setImageResource(getImageResource(exercise.category));
        holder.isFinishedCheckBox.setChecked(exercise.isFinished);

        holder.exercise = exercise;
    }

    //Itt mondjuk nekem ne, jó ez a megközelítés, mert minden gyakorlathoz külön kép jár és azt kell itt megjeleníteni és nem a katergóriát
    private @DrawableRes
    int getImageResource(Exercise.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case CROSSFIT:
                ret = R.drawable.weight128;
                break;
            case SMR:
                ret = R.drawable.weight128;
                break;
            case STRETCHING:
                ret = R.drawable.weight128;
                break;
            case WEIGHT_LIFTING:
                ret = R.drawable.weight128;
                break;
            case POWER_LIFTING:
                ret = R.drawable.weight128;
                break;
            case CARDIO:
                ret = R.drawable.weight128;
                break;
            case WARMUP:
                ret = R.drawable.weight128;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        notifyItemInserted(exercises.size() - 1);
    }

    public void update(List<Exercise> exercises) {
        this.exercises.clear();
        this.exercises.addAll(exercises);
        notifyDataSetChanged();
    }

    public int getPosition(final Exercise exercise) {
        return this.exercises.indexOf(exercise);
    }

    public void removeAt(int position){
        this.exercises.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.exercises.size());
    }

    public void deleteExercise(Exercise exercise){
        final int position = getPosition(exercise);
        this.exercises.remove(exercise);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }

}
