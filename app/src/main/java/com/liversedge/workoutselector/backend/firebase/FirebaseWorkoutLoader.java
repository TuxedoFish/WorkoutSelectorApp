package com.liversedge.workoutselector.backend.firebase;

import java.util.List;

public interface FirebaseWorkoutLoader {
    public void onTargetsLoadedFailure();
    public void onTargetsLoadedSuccess(List<String> targets);

    public void onEquipmentLoadedFailure();
    public void onEquipmentLoadedSuccess(List<String> equipment);

    public void onWorkoutLoaded();
}
