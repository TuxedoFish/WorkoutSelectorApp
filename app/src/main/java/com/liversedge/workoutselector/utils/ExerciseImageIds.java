package com.liversedge.workoutselector.utils;

import com.liversedge.workoutselector.R;

import java.util.HashMap;
import java.util.Map;

public class ExerciseImageIds {

    Map<String, Integer> nameToID = new HashMap<>();
    int defaultID = R.drawable.ic_info_24px;

    public ExerciseImageIds() {
        // Exercise
        nameToID.put("leg raise", R.drawable.leg_raises);
        nameToID.put("squat", R.drawable.squats);
        nameToID.put("bicep curl", R.drawable.bicep_curl);
        nameToID.put("jack knives", R.drawable.jack_knives);
        nameToID.put("mountain climber", R.drawable.mountain_climber);
        nameToID.put("up & down plank", R.drawable.plank);
        nameToID.put("plank", R.drawable.plank);
        nameToID.put("push up", R.drawable.push_up);
        nameToID.put("press up", R.drawable.push_up);
        nameToID.put("tricep dip", R.drawable.tricep_dips);
        nameToID.put("sit up", R.drawable.situp);
        nameToID.put("lunge", R.drawable.lunge);
        nameToID.put("burpee", R.drawable.burpee);
        nameToID.put("chest press", R.drawable.chestpress);
        // Cardio
        nameToID.put("run", R.drawable.run);
        // Descriptions
        nameToID.put("image_1", R.drawable.ic_looks_one_24px);
        nameToID.put("image_2", R.drawable.ic_looks_two_24px);
        nameToID.put("image_3", R.drawable.ic_looks_3_24px);
        nameToID.put("image_4", R.drawable.ic_looks_4_24px);
        nameToID.put("image_5", R.drawable.ic_looks_5_24px);
    }

    public Integer getImageByExerciseName(String name) {
        String w = name.toLowerCase();
        Integer n = w.length();

        // Try word and any other plural variants
        Integer resourceID = removePluralOptions(w);
        if(resourceID != null) { return resourceID; }

        String[] l = w.split(" ");

        // Try individual words
        for(int i=0; i<l.length; i++) {
            resourceID = removePluralOptions(l[i]);
            if(resourceID != null) { return resourceID; }
        }

        // Try consecutive double words
        for(int i=0; i<l.length-1; i++) {
            resourceID = removePluralOptions(l[i] + " " + l[i+1]);
            if(resourceID != null) { return resourceID; }
        }

        return resourceID==null ? defaultID : resourceID;
    }

    private Integer removePluralOptions(String w) {

        // Try the actual word
        Integer n = w.length();
        Integer resourceID = nameToID.get(w);
        if(resourceID != null) { return resourceID; }

        // Try taking -s of last word i.e
        // push ups => push up
        if(w.endsWith("s")) {
            resourceID = nameToID.get(w.substring(0, n-1));
            if(resourceID != null) {
                return resourceID;
            }
        }
        // Same with ending in -es i.e
        // crunches => crunch
        if(w.endsWith("es")) {
            resourceID = nameToID.get(w.substring(0, n-2));
            if(resourceID != null) {
                return resourceID;
            }
        }

        return null;
    }
}
