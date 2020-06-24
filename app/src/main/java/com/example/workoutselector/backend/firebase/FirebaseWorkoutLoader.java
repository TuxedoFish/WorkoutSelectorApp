package com.example.workoutselector.backend.firebase;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseWorkoutLoader {
    public void onTargetsLoadedFailure();
    public void onTargetsLoadedSuccess(List<String> targets);

    public void onEquipmentLoadedFailure();
    public void onEquipmentLoadedSuccess(List<String> equipment);
}
