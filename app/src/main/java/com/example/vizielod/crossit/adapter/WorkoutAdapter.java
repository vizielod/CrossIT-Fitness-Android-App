package com.example.vizielod.crossit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vizielod.crossit.DetailedWorkoutActivity;
import com.example.vizielod.crossit.R;
import com.example.vizielod.crossit.data.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter
        extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private final List<Workout> workouts;

    private WorkoutClickListener listener;
    private Context context;
    public WorkoutAdapter(WorkoutClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        workouts = new ArrayList<>();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.workout_list, parent, false);
        return new WorkoutViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public interface WorkoutClickListener{
        void onWorkoutChanged(Workout workout);
        void onWorkoutDeleted(Workout workout);
        void onWorkoutCreated(Workout workout);
    }


    //Ezeken a mezőkön keresztül tudjuk majd elérni az egyes lista elemekhez tartozó nézeteket
    class WorkoutViewHolder extends RecyclerView.ViewHolder {

        ImageButton workoutListElementButton;
        TextView workoutNameTextView;
        ImageButton workoutRemoveButton;

        Workout workout;

        WorkoutViewHolder(View itemView) {
            super(itemView);
            workoutListElementButton = itemView.findViewById(R.id.WorkoutListElementButton);
            workoutNameTextView = itemView.findViewById(R.id.WorkoutNameTextView);
            workoutRemoveButton = itemView.findViewById(R.id.WorkoutRemoveButton);
            workoutListElementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailedWorkoutIntent = new Intent(context, DetailedWorkoutActivity.class);
                    detailedWorkoutIntent.putExtra(DetailedWorkoutActivity.KEY_WORKOUT_ID, workout.getID()/*.longValue()*/);
                    context.startActivity(detailedWorkoutIntent);

                }
            });
            workoutRemoveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(workout != null) {
                        deleteWorkout(workout);
                        listener.onWorkoutDeleted(workout);
                        Snackbar snackbarDelete = Snackbar.make(v, R.string.workout_deleted_message, Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        Snackbar snackbarUndo = Snackbar.make(view, R.string.workout_restored_message, Snackbar.LENGTH_LONG);
                                        snackbarUndo.show();
                                        listener.onWorkoutCreated(workout);
                                    }
                                });
                        snackbarDelete.show();
                    }
                }
            });

        }

    }

    //Kössük hozzá a megfelelő modell elem tulajdonságait a lista elem nézeteihez.
    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        holder.workoutNameTextView.setText(workout.name);
        holder.workoutListElementButton.setImageResource(getImageResource(workout.category));

        holder.workout = workout;
    }

    //Itt mondjuk nekem ne, jó ez a megközelítés, mert minden gyakorlathoz külön kép jár és azt kell itt megjeleníteni és nem a katergóriát
    private @DrawableRes
    int getImageResource(Workout.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case CROSSFIT:
                ret = R.drawable.crossfit;
                break;
            case WEIGHT_TRAINING:
                ret = R.drawable.weight_training_resized;
                break;
            case FUNCTIONAL_CROSS_TRAINING:
                ret = R.drawable.functional_training;
                break;
            case WEIGHT_LIFTING:
                ret = R.drawable.weight_lifting;
                break;
            case POWER_LIFTING:
                ret = R.drawable.power_lifting;
                break;
            case BODYWEIGHT_WORKOUTS:
                ret = R.drawable.bodyweight_training;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    public void addWorkout(Workout workout) {
        workouts.add(workout);
        notifyItemInserted(workouts.size() - 1);
    }

    public void update(List<Workout> workouts) {
        this.workouts.clear();
        this.workouts.addAll(workouts);
        notifyDataSetChanged();
    }

    /*public void updateByCategoryChanged(List<Workout> workouts, int category){
        this.workouts.clear();
        for(int i=0; i < workouts.size(); i++){
            if( )
        }
    }*/

    public int getPosition(final Workout workout) {
        return this.workouts.indexOf(workout);
    }

    public void removeAt(int position){
        this.workouts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.workouts.size());
    }

    public void deleteWorkout(Workout workout){
        final int position = getPosition(workout);
        this.workouts.remove(workout);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }
    
}
