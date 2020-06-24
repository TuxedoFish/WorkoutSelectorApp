package com.example.workoutselector.utils;

import com.example.workoutselector.backend.firebase.WorkoutDAO;
import com.example.workoutselector.frontend.adapters.ExerciseItem;

import java.util.ArrayList;

public class DummyData {

    public static WorkoutDAO getDummyWorkout() {
        // TODO: Implement tests
        return null;
    }

    public static String[] getEquipment() {
        String[] areas = {
                "Dumbell",
                "Ropes",
                "Bench",
                "Barbell",
                "Mat",
                "Pull up bar"
        };

        return areas;
    }

}
