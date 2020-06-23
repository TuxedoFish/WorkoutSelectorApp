package com.example.workoutselector.backend.firebase;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseWorkoutLoader {
    public void onWorkoutsLoadedFailure();
    public void onWorkoutsLoadedSuccess(ArrayList<WorkoutDAO> workouts);

    public void onTargetsLoadedFailure();
    public void onTargetsLoadedSuccess(List<String> targets);

    public void onEquipmentLoadedFailure();
    public void onEquipmentLoadedSuccess(List<String> equipment);
}
