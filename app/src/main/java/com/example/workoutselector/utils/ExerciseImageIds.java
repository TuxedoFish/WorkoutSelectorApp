package com.example.workoutselector.utils;

import com.example.workoutselector.R;

import java.util.HashMap;
import java.util.Map;

public class ExerciseImageIds {

    Map<String, Integer> nameToID = new HashMap<>();
    int defaultID = R.drawable.squats;

    public ExerciseImageIds() {
        nameToID.put("Leg raises", R.drawable.leg_raises);
        nameToID.put("Squats", R.drawable.squats);
        nameToID.put("Bicep curls", R.drawable.bicep_curl);
        nameToID.put("Jack knives", R.drawable.jack_knives);
        nameToID.put("Mountain climbers", R.drawable.mountain_climber);
        nameToID.put("Up & down plank", R.drawable.plank);
        nameToID.put("Plank", R.drawable.plank);
        nameToID.put("Push ups", R.drawable.push_up);
        nameToID.put("Tricep dips", R.drawable.tricep_dips);
    }

    public Integer getImageByExerciseName(String name) {
        Integer resourceID = nameToID.get(name);
        return resourceID==null ? defaultID : resourceID;
    }
}
